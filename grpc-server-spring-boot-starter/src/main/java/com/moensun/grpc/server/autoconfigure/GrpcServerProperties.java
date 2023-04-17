package com.moensun.grpc.server.autoconfigure;

import lombok.Data;

@Data
public class GrpcServerProperties {
    private int port = 50051;
}
