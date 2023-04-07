package com.moensun.grpc.server;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.moensun.grpc.server.annotations.GrpcGlobalServerInterceptor;
import com.moensun.grpc.server.annotations.GrpcService;
import com.moensun.grpc.server.autoconfigure.GrpcServerConfProperties;
import com.moensun.grpc.server.autoconfigure.GrpcServerProperties;
import io.grpc.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.util.LinkedMultiValueMap;

import java.io.IOException;
import java.util.*;

@Slf4j
public class GrpcServerLifecycle implements SmartLifecycle {
    private boolean running = false;
    private final String DEFAULT_GRPC_SERVER_NAME = "defaultGrpcServer";

    private final GrpcServerConfProperties grpcServerConfProperties;
    private final GenericApplicationContext applicationContext;

    private final Map<String, Server> grpcServers = new HashMap<>();

    public GrpcServerLifecycle(GrpcServerConfProperties grpcServerConfProperties, GenericApplicationContext applicationContext) {
        this.grpcServerConfProperties = grpcServerConfProperties;
        this.applicationContext = applicationContext;
    }


    @Override
    public void start() {
        try {
            createAndStartServers();
            this.running = true;
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() {
        stopServers();
    }

    @Override
    public boolean isRunning() {
        return this.running;
    }

    @Override
    public void stop(Runnable callback) {
        stopServers();
        callback.run();
    }

    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public int getPhase() {
        return Integer.MAX_VALUE;
    }


    protected void createAndStartServers() throws IOException, InterruptedException {
        LinkedMultiValueMap<String, ServerServiceDefinition> serverServiceDefinitions = findBindableServices();
        LinkedMultiValueMap<String, ServerInterceptor> serverInterceptorsMap = findServerInterceptors();
        Map<String, GrpcServerProperties> grpcServersMap = grpcServerConfProperties.getMulti();
        if (CollectionUtil.isEmpty(grpcServersMap)) {
            String serverName = StrUtil.isNotBlank(grpcServerConfProperties.getServerName()) ? grpcServerConfProperties.getServerName() : DEFAULT_GRPC_SERVER_NAME;
            Server server = createAndStartServer(grpcServerConfProperties, serverServiceDefinitions.get(DEFAULT_GRPC_SERVER_NAME), serverInterceptorsMap.get(DEFAULT_GRPC_SERVER_NAME));
            awaitTermination(server);
            grpcServers.put(serverName, server);
        } else {
            for (Map.Entry<String, GrpcServerProperties> entry : grpcServersMap.entrySet()) {
                String k = entry.getKey();
                GrpcServerProperties v = entry.getValue();
                String serverName = StrUtil.isNotBlank(v.getServerName()) ? v.getServerName() : k;
                Server server = createAndStartServer(v, serverServiceDefinitions.get(serverName), serverInterceptorsMap.get(serverName));
                awaitTermination(server);
                grpcServers.put(serverName, server);
            }
        }
    }

    protected Server createAndStartServer(GrpcServerProperties grpcServerProperties, Collection<ServerServiceDefinition> bindableServices, Collection<ServerInterceptor> serverInterceptors) throws IOException {
        ServerBuilder<?> serverBuilder = ServerBuilder.forPort(grpcServerProperties.getPort());
        if (CollectionUtil.isNotEmpty(bindableServices)) {
            bindableServices.forEach(serverBuilder::addService);
        }
        if (CollectionUtil.isNotEmpty(serverInterceptors)) {
            serverInterceptors.forEach(serverBuilder::intercept);
        }
        return serverBuilder.build().start();
    }

    protected LinkedMultiValueMap<String, ServerServiceDefinition> findBindableServices() {
        LinkedMultiValueMap<String, ServerServiceDefinition> bindableServiceMap = new LinkedMultiValueMap<>();
        Arrays.stream(applicationContext.getBeanNamesForType(BindableService.class)).forEach(bindableServiceName -> {
            GrpcService grpcService = applicationContext.findAnnotationOnBean(bindableServiceName, GrpcService.class);
            BindableService bindableService = applicationContext.getBean(bindableServiceName, BindableService.class);
            if (Objects.isNull(grpcService)) {
                bindableServiceMap.add(DEFAULT_GRPC_SERVER_NAME, bindableService.bindService());
            } else if (StrUtil.isBlank(grpcService.serverName())) {
                bindableServiceMap.add(DEFAULT_GRPC_SERVER_NAME, serverServiceDefinition(grpcService, bindableService));
            } else {
                bindableServiceMap.add(grpcService.serverName(), serverServiceDefinition(grpcService, bindableService));
            }
        });
        return bindableServiceMap;
    }

    protected ServerServiceDefinition serverServiceDefinition(GrpcService grpcService, BindableService bindableService) {
        final List<ServerInterceptor> interceptors = Lists.newArrayList();
        for (Class<? extends ServerInterceptor> interceptorClass : grpcService.interceptors()) {
            final ServerInterceptor serverInterceptor;
            if (this.applicationContext.getBeanNamesForType(interceptorClass).length == 0) {
                applicationContext.registerBean(interceptorClass);
            }
            serverInterceptor = this.applicationContext.getBean(interceptorClass);
            interceptors.add(serverInterceptor);
        }
        return ServerInterceptors.interceptForward(bindableService.bindService(), interceptors);
    }

    protected LinkedMultiValueMap<String, ServerInterceptor> findServerInterceptors() {
        LinkedMultiValueMap<String, ServerInterceptor> serverInterceptorLinkedMultiValueMap = new LinkedMultiValueMap<>();
        Arrays.stream(applicationContext.getBeanNamesForType(ServerInterceptor.class)).forEach(serverInterceptorName -> {
            GrpcGlobalServerInterceptor grpcGlobalServerInterceptor = applicationContext.findAnnotationOnBean(serverInterceptorName, GrpcGlobalServerInterceptor.class);
            if (Objects.nonNull(grpcGlobalServerInterceptor)) {
                ServerInterceptor serverInterceptor = applicationContext.getBean(serverInterceptorName, ServerInterceptor.class);
                if (StrUtil.isBlank(grpcGlobalServerInterceptor.serverName())) {
                    serverInterceptorLinkedMultiValueMap.add(DEFAULT_GRPC_SERVER_NAME, serverInterceptor);
                } else {
                    serverInterceptorLinkedMultiValueMap.add(grpcGlobalServerInterceptor.serverName(), serverInterceptor);
                }
            }
        });
        return serverInterceptorLinkedMultiValueMap;
    }

    protected void awaitTermination(Server server) {
        Thread awaitThread = new Thread(() -> {
            try {
                server.awaitTermination();
            } catch (InterruptedException e) {
                log.error("gRPC server stopped.", e);
            }
        });
        awaitThread.setDaemon(false);
        awaitThread.start();
    }

    protected void stopServers() {
        if (CollectionUtil.isNotEmpty(grpcServers)) {
            grpcServers.forEach((k, v) -> {
                try {
                    v.shutdown().awaitTermination();
                    log.info("grpc shutdown");
                } catch (InterruptedException e) {
                    e.printStackTrace(System.err);
                }
            });
        }
    }
}
