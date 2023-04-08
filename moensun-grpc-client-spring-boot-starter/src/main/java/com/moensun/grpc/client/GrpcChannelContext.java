package com.moensun.grpc.client;

import org.springframework.cloud.context.named.NamedContextFactory;

public class GrpcChannelContext extends NamedContextFactory<GrpcStubSpecification> {
    public GrpcChannelContext() {
        super(GrpcChannelConfiguration.class, "channel", "name");
    }
}
