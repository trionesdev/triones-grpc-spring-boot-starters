package com.moensun.grpc.server.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "grpc.server", ignoreUnknownFields = true)
public class GrpcServerProperties {
    private int port = 50051;
}
