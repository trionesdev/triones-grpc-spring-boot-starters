package com.moensun.grpc.server.autoconfigure;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@ConfigurationProperties(prefix = "grpc.server", ignoreUnknownFields = true)
public class GrpcServerConfProperties extends GrpcServerProperties {
    private Map<String, GrpcServerProperties> multi;
}
