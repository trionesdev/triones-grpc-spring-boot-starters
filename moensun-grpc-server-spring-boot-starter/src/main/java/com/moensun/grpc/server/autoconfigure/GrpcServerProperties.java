package com.moensun.grpc.server.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "grpc.server", ignoreUnknownFields = true)
public class GrpcServerProperties extends GrpcServerInstanceProperties{
    private Map<String,GrpcServerInstanceProperties> multi;
}
