package com.moensun.grpc.client;

import com.moensun.grpc.client.annotations.GrpcClient;
import io.opentelemetry.example.grpc.GreeterGrpc;
import io.opentelemetry.example.grpc.HelloReply;
import io.opentelemetry.example.grpc.HelloRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GrpcStubService {

    private final   GreeterGrpc.GreeterBlockingStub greeterBlockingStub;

    public void say(){
        HelloReply reply = greeterBlockingStub.sayHello(HelloRequest.newBuilder().setName("ms grpc client").build());
    }

}
