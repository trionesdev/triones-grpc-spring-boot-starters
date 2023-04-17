package com.moensun.grpc.client;

import com.moensun.grpc.client.annotations.EnableGrpcChannels;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableGrpcChannels
public class GrpcStubSpringBootApp {

    public static void main(String[] args) {
        SpringApplication.run(GrpcStubSpringBootApp.class, args);
    }

}
