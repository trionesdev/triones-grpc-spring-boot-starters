package com.trionesdev.grpc.client.autoconfigure;

import com.trionesdev.grpc.client.GrpcChannelContext;
import com.trionesdev.grpc.client.GrpcChannelSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class GrpcChannelAutoConfiguration {

    @Autowired(required = false)
    private List<GrpcChannelSpecification> configurations = new ArrayList<>();

    @Bean
    public GrpcChannelContext grpcChannelContext(){
        GrpcChannelContext context = new GrpcChannelContext();
        context.setConfigurations(this.configurations);
        return context;
    }

}
