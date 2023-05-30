package com.moensun.grpc.client;

import com.moensun.grpc.client.annotation.GrpcChannel;
import io.opentelemetry.example.grpc.GreeterGrpc;


@GrpcChannel(name = "bgrpc-channel",target="localhost:50051",stubs = {

})
public interface BaGrpcStubs {
//    private final ManagedChannel channel;
}
