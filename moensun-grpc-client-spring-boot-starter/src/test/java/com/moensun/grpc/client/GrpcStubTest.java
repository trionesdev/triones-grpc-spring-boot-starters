package com.moensun.grpc.client;

import io.opentelemetry.example.grpc.GreeterGrpc;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@RequiredArgsConstructor
@SpringBootTest
@ActiveProfiles("test")
public class GrpcStubTest {

    @Autowired
    private GrpcStubService grpcClientService;

    @Test
    public void client_test(){
        grpcClientService.say();
    }

}
