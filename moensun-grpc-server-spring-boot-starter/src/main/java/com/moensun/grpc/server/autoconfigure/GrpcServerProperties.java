package com.moensun.grpc.server.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@ConfigurationProperties(prefix = "grpc.server", ignoreUnknownFields = true)
public class GrpcServerProperties extends GrpcServerInstanceProperties{
    private Map<String,GrpcServerInstanceProperties> multi;
}
