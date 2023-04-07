package com.moensun.grpc.client.autoconfigure;

import com.moensun.grpc.client.GrpcClientBeanPostProcessor;
import com.moensun.grpc.client.annotations.GrpcClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;

@Slf4j
@RequiredArgsConstructor
@Configuration
//@ConditionalOnBean(annotation = GrpcClient.class)
@EnableConfigurationProperties(value = {GrpcClientConfProperties.class})
public class GrpcClientAutoConfiguration {

    @Bean
    public GrpcClientBeanPostProcessor grpcClientBeanPostProcessor(GenericApplicationContext applicationContext) {
        return new GrpcClientBeanPostProcessor(applicationContext);
    }


}
