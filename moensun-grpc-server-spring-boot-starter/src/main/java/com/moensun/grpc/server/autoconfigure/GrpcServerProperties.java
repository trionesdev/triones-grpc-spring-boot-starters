package com.moensun.grpc.server.autoconfigure;

import lombok.Data;

@Data
public class GrpcServerProperties {
    private String serverName;
    private int port = 50051;
}
