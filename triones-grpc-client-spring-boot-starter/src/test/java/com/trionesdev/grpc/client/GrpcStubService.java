package com.trionesdev.grpc.client;

import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.opentelemetry.example.grpc.GreeterGrpc;
import io.opentelemetry.example.grpc.HelloReply;
import io.opentelemetry.example.grpc.HelloRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GrpcStubService {

    private final GreeterGrpc.GreeterBlockingStub greeterBlockingStub;

    public void say(){
        Channel c = ManagedChannelBuilder.forTarget("localhost:50051").usePlaintext().build();
        GreeterGrpc.GreeterBlockingStub greeterBlockingStub1 = GreeterGrpc.newBlockingStub(c);
        HelloReply reply = greeterBlockingStub.sayHello(HelloRequest.newBuilder().setName("ms grpc client").build());
    }

}
