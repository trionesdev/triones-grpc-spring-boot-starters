package com.moensun.grpc.clientt;

import com.moensun.grpc.client.annotations.GrpcClient;
import io.opentelemetry.example.grpc.GreeterGrpc;
import io.opentelemetry.example.grpc.HelloReply;
import io.opentelemetry.example.grpc.HelloRequest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.ActiveProfiles;

@RequiredArgsConstructor
@SpringBootTest
@ActiveProfiles("test")
public class GrpcClientTest {

    @GrpcClient
    private final GreeterGrpc.GreeterBlockingStub greeterBlockingStub;

    @Test
    public void client_test(){
//        GenericApplicationContext s ;
//        s.registerBean(GreeterGrpc.GreeterBlockingStub.class );
        HelloReply reply = greeterBlockingStub.sayHello(HelloRequest.newBuilder().setName("ms grpc client").build());
    }

}
