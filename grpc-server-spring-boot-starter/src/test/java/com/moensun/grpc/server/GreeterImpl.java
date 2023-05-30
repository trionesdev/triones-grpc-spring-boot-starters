package com.moensun.grpc.server;

import com.moensun.grpc.server.annotation.GrpcService;
import io.grpc.stub.StreamObserver;
import io.opentelemetry.example.grpc.GreeterGrpc;
import io.opentelemetry.example.grpc.HelloReply;
import io.opentelemetry.example.grpc.HelloRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@GrpcService(interceptors = {})
public class GreeterImpl extends GreeterGrpc.GreeterImplBase {
    @Override
    public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
        // Serve the request
        HelloReply reply = HelloReply.newBuilder().setMessage("Hello " + req.getName()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    // We serve a stream gRPC call
    @Override
    public StreamObserver<HelloRequest> sayHelloStream(
            final StreamObserver<HelloReply> responseObserver) {
        return new StreamObserver<HelloRequest>() {
            @Override
            public void onNext(HelloRequest value) {
                responseObserver.onNext(
                        HelloReply.newBuilder().setMessage("Hello " + value.getName()).build());
            }

            @Override
            public void onError(Throwable t) {
                log.info("[Error] " + t.getMessage());
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
