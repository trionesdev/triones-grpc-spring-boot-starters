package com.moensun.grpc.server;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import com.moensun.grpc.server.autoconfigure.GrpcServerInstanceProperties;
import com.moensun.grpc.server.autoconfigure.GrpcServerProperties;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.CommandLineRunner;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class GrpcServerRunner implements CommandLineRunner, DisposableBean {
    private final GrpcServerProperties  grpcServerProperties;

    private Map<String, Server> grpcServers = new HashMap<>();

    public GrpcServerRunner(GrpcServerProperties grpcServerProperties) {
        this.grpcServerProperties = grpcServerProperties;
    }

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void run(String... args) throws Exception {
        Map<String, GrpcServerInstanceProperties> grpcServerMap = grpcServerProperties.getMulti();
        if(CollectionUtil.isEmpty(grpcServerMap)){
            Server server = ServerBuilder.forPort(grpcServerProperties.getPort()).build();
            grpcServers.put("defaultGrpcServer",server);
        }else {

        }



        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
//                if (Objects.nonNull(server)) {
//                    server.shutdown().awaitTermination();
//                }

                if (CollectionUtil.isNotEmpty(grpcServers)){
//                    grpcServers.entrySet().
                }

            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
            log.info("grpc server shutdown");
        }));
    }
}
