package com.moensun.grpc.server;

import com.moensun.grpc.server.autoconfigure.GrpcServerProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.CommandLineRunner;

@Slf4j
public class GrpcServerRunner implements CommandLineRunner, DisposableBean {
    private final GrpcServerProperties  grpcServerProperties;

    public GrpcServerRunner(GrpcServerProperties grpcServerProperties) {
        this.grpcServerProperties = grpcServerProperties;
    }

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void run(String... args) throws Exception {

    }
}
