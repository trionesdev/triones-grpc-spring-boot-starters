package com.trionesdev.grpc.server.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@Bean
public @interface GrpcGlobalServerInterceptor {
    String serverName() default "";
}
