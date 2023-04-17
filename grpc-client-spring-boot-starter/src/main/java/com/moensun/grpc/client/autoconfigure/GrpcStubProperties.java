package com.moensun.grpc.client.autoconfigure;

import lombok.Data;

@Data
public class GrpcStubProperties {
    private String serverName;
    private String address;
    private int port = 50051;
}
