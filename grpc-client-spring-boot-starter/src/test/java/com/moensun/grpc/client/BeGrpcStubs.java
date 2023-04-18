package com.moensun.grpc.client;

import com.moensun.grpc.client.annotation.GrpcChannel;
import io.opentelemetry.example.grpc.GreeterGrpc;


@GrpcChannel(name = "sddsss",stubs = {
        GreeterGrpc.GreeterBlockingStub.class
})
public interface BeGrpcStubs {
//    private final ManagedChannel channel;
}
