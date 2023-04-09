package com.moensun.grpc.client.autoconfigure;

import com.moensun.grpc.client.GrpcChannelContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
//@ConditionalOnBean(annotation = GrpcClient.class)
@EnableConfigurationProperties(value = {GrpcStubConfProperties.class})
public class GrpcChannelAutoConfiguration {

//    @Bean
//    public GrpcClientBeanPostProcessor grpcClientBeanPostProcessor(GenericApplicationContext applicationContext) {
//        return new GrpcClientBeanPostProcessor(applicationContext);
//    }

    @Bean
    public GrpcChannelContext grpcChannelContext(){
        GrpcChannelContext context = new GrpcChannelContext();
        return context;
    }

}
