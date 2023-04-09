package com.moensun.grpc.client;

import com.moensun.grpc.client.annotations.GrpcChannel;
import io.grpc.ManagedChannel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@GrpcChannel
public class BeGrpcStubs {
    private final ManagedChannel channel;
}
