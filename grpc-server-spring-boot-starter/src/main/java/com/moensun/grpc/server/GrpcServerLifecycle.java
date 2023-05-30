package com.moensun.grpc.server;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.moensun.grpc.server.annotation.GrpcGlobalServerInterceptor;
import com.moensun.grpc.server.annotation.GrpcService;
import com.moensun.grpc.server.autoconfigure.GrpcServerProperties;
import io.grpc.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.support.GenericApplicationContext;

import java.io.IOException;
import java.util.*;

@Slf4j
public class GrpcServerLifecycle implements SmartLifecycle {
    private boolean running = false;

    private final GrpcServerProperties grpcServerConfProperties;
    private final GenericApplicationContext applicationContext;

    private final Map<String, Server> grpcServers = new HashMap<>();
    private Server server;

    public GrpcServerLifecycle(GrpcServerProperties grpcServerConfProperties, GenericApplicationContext applicationContext) {
        this.grpcServerConfProperties = grpcServerConfProperties;
        this.applicationContext = applicationContext;
    }


    @Override
    public void start() {
        try {
            server = createAndStartServer(grpcServerConfProperties);
            awaitTermination(server);
            this.running = true;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() {
        stopServer();
    }

    @Override
    public boolean isRunning() {
        return this.running;
    }

    @Override
    public void stop(Runnable callback) {
        stopServer();
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

    protected Server createAndStartServer(GrpcServerProperties grpcServerProperties) throws IOException {
        Collection<ServerServiceDefinition> bindableServices = findBindableServices();
        Collection<ServerInterceptor> globalServerInterceptors = findGlobalServerInterceptors();
        ServerBuilder<?> serverBuilder = ServerBuilder.forPort(grpcServerProperties.getPort());
        if (CollectionUtil.isNotEmpty(bindableServices)) {
            bindableServices.forEach(serverBuilder::addService);
        }
        if (CollectionUtil.isNotEmpty(globalServerInterceptors)) {
            globalServerInterceptors.forEach(serverBuilder::intercept);
        }
        return serverBuilder.build().start();
    }

    protected List<ServerServiceDefinition> findBindableServices() {
        List<ServerServiceDefinition> serverServiceDefinitions = new ArrayList<>();
        Arrays.stream(applicationContext.getBeanNamesForType(BindableService.class)).forEach(bindableServiceName -> {
            GrpcService grpcService = applicationContext.findAnnotationOnBean(bindableServiceName, GrpcService.class);
            BindableService bindableService = applicationContext.getBean(bindableServiceName, BindableService.class);
            if (Objects.isNull(grpcService)) {
                serverServiceDefinitions.add(bindableService.bindService());
            } else {
                serverServiceDefinitions.add(serverServiceDefinition(grpcService, bindableService));
            }
        });
        return serverServiceDefinitions;
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

    protected List<ServerInterceptor> findGlobalServerInterceptors() {
        List<ServerInterceptor> globalServerInterceptors = new ArrayList<>();
        Arrays.stream(applicationContext.getBeanNamesForType(ServerInterceptor.class)).forEach(serverInterceptorName -> {
            GrpcGlobalServerInterceptor grpcGlobalServerInterceptor = applicationContext.findAnnotationOnBean(serverInterceptorName, GrpcGlobalServerInterceptor.class);
            if (Objects.nonNull(grpcGlobalServerInterceptor)) {
                ServerInterceptor serverInterceptor = applicationContext.getBean(serverInterceptorName, ServerInterceptor.class);
                globalServerInterceptors.add(serverInterceptor);
            }
        });
        return globalServerInterceptors;
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

    protected void stopServer() {
        if (Objects.isNull(server) || server.isShutdown()) {
            return;
        }
        try {
            server.shutdown().awaitTermination();
            log.info("grpc shutdown");
        } catch (InterruptedException e) {
            e.printStackTrace(System.err);
        }
    }
}
