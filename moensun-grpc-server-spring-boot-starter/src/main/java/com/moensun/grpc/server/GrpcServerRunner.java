package com.moensun.grpc.server;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.moensun.grpc.server.autoconfigure.GrpcServerProperties;
import com.moensun.grpc.server.autoconfigure.GrpcServerConfProperties;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.CommandLineRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class GrpcServerRunner implements CommandLineRunner, DisposableBean {
    private final GrpcServerConfProperties grpcServerConfProperties;

    private Map<String, Server> grpcServers = new HashMap<>();

    public GrpcServerRunner(GrpcServerConfProperties grpcServerConfProperties) {
        this.grpcServerConfProperties = grpcServerConfProperties;
    }

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void run(String... args) throws Exception {
        Map<String, GrpcServerProperties> grpcServersMap = grpcServerConfProperties.getMulti();
        if (CollectionUtil.isEmpty(grpcServersMap)) {
            String serverName = StrUtil.isNotBlank(grpcServerConfProperties.getServerName()) ? grpcServerConfProperties.getServerName() : "defaultGrpcServer";
            Server server = startServer(grpcServerConfProperties);
            grpcServers.put(serverName, server);
        } else {
            for (Map.Entry<String, GrpcServerProperties> entry : grpcServersMap.entrySet()) {
                String k = entry.getKey();
                GrpcServerProperties v = entry.getValue();
                String serverName = StrUtil.isNotBlank(v.getServerName()) ? v.getServerName() : k;
                Server server = startServer(v);
                grpcServers.put(serverName, server);
            }
        }


        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
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
        }));
    }

    private Server startServer(GrpcServerProperties grpcServerProperties) throws IOException {
        return ServerBuilder.forPort(grpcServerProperties.getPort())
                .build()
                .start();
    }
}
