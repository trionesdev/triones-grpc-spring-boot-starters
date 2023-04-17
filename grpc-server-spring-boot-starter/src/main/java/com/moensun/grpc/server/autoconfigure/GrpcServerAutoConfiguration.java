package com.moensun.grpc.server.autoconfigure;

import com.moensun.grpc.server.GrpcServerLifecycle;
import com.moensun.grpc.server.annotations.GrpcService;
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
@EnableConfigurationProperties(value = {GrpcServerConfProperties.class})
public class GrpcServerAutoConfiguration {
    private final GrpcServerConfProperties serverProperties;

    @Bean
    public GrpcServerLifecycle grpcServerLifecycle(GenericApplicationContext applicationContext) {
        return new GrpcServerLifecycle(serverProperties, applicationContext);
    }

}
