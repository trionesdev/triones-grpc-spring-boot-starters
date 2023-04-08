package com.moensun.grpc.client;

import com.moensun.grpc.client.annotations.EnableGrpcStubs;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableGrpcStubs
public class GrpcStubSpringBootApp {

    public static void main(String[] args) {
        SpringApplication.run(GrpcStubSpringBootApp.class, args);
    }

}
