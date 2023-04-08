package com.moensun.grpc.client;

import com.moensun.grpc.client.annotations.GrpcStubs;
import io.grpc.ManagedChannel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@GrpcStubs
public class BeGrpcStubs {
    private final ManagedChannel channel;
}
