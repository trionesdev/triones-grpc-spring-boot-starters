package com.moensun.grpc.server.autoconfigure;

import com.moensun.grpc.server.GrpcServerRunner;
import com.moensun.grpc.server.annotations.GrpcService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
@ConditionalOnBean(annotation = GrpcService.class)
@EnableConfigurationProperties(value = {GrpcServerProperties.class})
public class GrpcServerAutoConfiguration {
    private final GrpcServerProperties serverProperties;

    @Bean
    public GrpcServerRunner grpcServerRunner(){
        return new GrpcServerRunner(serverProperties);
    }

}
