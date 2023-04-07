package com.moensun.grpc.client;

import cn.hutool.core.collection.CollectionUtil;
import com.moensun.grpc.client.autoconfigure.GrpcClientConfProperties;
import com.moensun.grpc.client.autoconfigure.GrpcClientProperties;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class GrpcClientLifecycle implements SmartLifecycle {
    private boolean running = false;
    private final String DEFAULT_GRPC_CLIENT_NAME = "defaultGrpcClient";
    private final GrpcClientConfProperties grpcClientConfProperties;
    private final GenericApplicationContext applicationContext;
    private final Map<String, ManagedChannel> channelMap = new HashMap<>();
    ;

    public GrpcClientLifecycle(GrpcClientConfProperties grpcClientConfProperties,GenericApplicationContext applicationContext) {
        this.grpcClientConfProperties = grpcClientConfProperties;
        this.applicationContext = applicationContext;
    }

    @Override
    public void start() {
        this.running = true;
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public boolean isAutoStartup() {
        return SmartLifecycle.super.isAutoStartup();
    }

    @Override
    public void stop(Runnable callback) {
        SmartLifecycle.super.stop(callback);
    }

    @Override
    public int getPhase() {
        return SmartLifecycle.super.getPhase();
    }

    protected void createChannels() {
//        Map<String, GrpcClientProperties> grpcClientPropertiesMap = grpcClientConfProperties.getMulti();
//        if (CollectionUtil.isEmpty(grpcClientPropertiesMap)) {
//            String target = grpcClientConfProperties.getAddress()+":"+grpcClientConfProperties.getPort();
//            ManagedChannel channel = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create())
//                    .build();
//            channelMap.put(DEFAULT_GRPC_CLIENT_NAME,channel);
//            AnnotationUtils.
//        } else {
//
//        }
    }


}
