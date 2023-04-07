package com.moensun.grpc.clientt;

import com.moensun.grpc.client.GrpcRegister;
import com.moensun.grpc.client.annotations.GrpcClient;
import io.opentelemetry.example.grpc.GreeterGrpc;
import io.opentelemetry.example.grpc.HelloReply;
import io.opentelemetry.example.grpc.HelloRequest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.ActiveProfiles;

@RequiredArgsConstructor
@SpringBootTest
@ActiveProfiles("test")
public class GrpcClientTest {

    @Autowired
    private GrpcClientService grpcClientService;

    @Test
    public void client_test(){
        grpcClientService.say();
    }

}
