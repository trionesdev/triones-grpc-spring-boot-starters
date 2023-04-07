package com.moensun.grpc.client.autoconfigure;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@ConfigurationProperties(prefix = "grpc.client", ignoreUnknownFields = true)
public class GrpcClientConfProperties extends GrpcClientProperties {
    private Map<String, GrpcClientProperties> multi;
}
