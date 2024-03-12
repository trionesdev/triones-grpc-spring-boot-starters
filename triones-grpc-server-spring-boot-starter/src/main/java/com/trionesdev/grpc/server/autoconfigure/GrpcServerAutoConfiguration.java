package com.trionesdev.grpc.server.autoconfigure;

import com.trionesdev.grpc.server.GrpcServerLifecycle;
import com.trionesdev.grpc.server.annotation.GrpcService;
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
@ConditionalOnBean(annotation = GrpcService.class)
@EnableConfigurationProperties(value = {GrpcServerProperties.class})
public class GrpcServerAutoConfiguration {
    private final GrpcServerProperties serverProperties;

    @Bean
    public GrpcServerLifecycle grpcServerLifecycle(GenericApplicationContext applicationContext) {
        return new GrpcServerLifecycle(serverProperties, applicationContext);
    }

}
