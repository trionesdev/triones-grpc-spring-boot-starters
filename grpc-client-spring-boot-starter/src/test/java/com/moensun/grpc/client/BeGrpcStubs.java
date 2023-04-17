package com.moensun.grpc.client;

import com.moensun.grpc.client.annotations.GrpcChannel;
import io.grpc.ManagedChannel;
import io.opentelemetry.example.grpc.GreeterGrpc;
import lombok.RequiredArgsConstructor;


@GrpcChannel(name = "sddsss",stubs = {
        GreeterGrpc.GreeterBlockingStub.class
})
public interface BeGrpcStubs {
//    private final ManagedChannel channel;
}
