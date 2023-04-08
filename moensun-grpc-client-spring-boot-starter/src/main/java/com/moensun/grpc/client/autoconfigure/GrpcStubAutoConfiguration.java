package com.moensun.grpc.client.autoconfigure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
//@ConditionalOnBean(annotation = GrpcClient.class)
@EnableConfigurationProperties(value = {GrpcStubConfProperties.class})
public class GrpcStubAutoConfiguration {

//    @Bean
//    public GrpcClientBeanPostProcessor grpcClientBeanPostProcessor(GenericApplicationContext applicationContext) {
//        return new GrpcClientBeanPostProcessor(applicationContext);
//    }


}
