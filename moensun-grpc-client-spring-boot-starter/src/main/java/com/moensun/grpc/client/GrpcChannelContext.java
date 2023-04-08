package com.moensun.grpc.client;

import org.springframework.cloud.context.named.NamedContextFactory;

public class GrpcChannelContext extends NamedContextFactory<GrpcChannelSpecification> {
    public GrpcChannelContext() {
        super(GrpcChannelConfiguration.class, "grpc", "grpc.channel.name");
    }
}
