package com.trionesdev.grpc.client;

import com.trionesdev.grpc.client.annotation.EnableGrpcChannels;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableGrpcChannels
public class GrpcStubSpringBootApp {

    public static void main(String[] args) {
        SpringApplication.run(GrpcStubSpringBootApp.class, args);
    }

}
