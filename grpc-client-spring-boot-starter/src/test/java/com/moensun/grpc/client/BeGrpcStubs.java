package com.moensun.grpc.client;

import com.moensun.grpc.client.annotation.GrpcChannel;
import io.opentelemetry.example.grpc.GreeterGrpc;


@GrpcChannel(name = "sddsss",target="localhost:50051",stubs = {
        GreeterGrpc.GreeterBlockingStub.class
})
public interface BeGrpcStubs {
//    private final ManagedChannel channel;
}
