package com.moensun.grpc.server.annotations;

import io.grpc.ServerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
@Bean
public @interface GrpcService {
    @AliasFor(annotation = Component.class)
    String value() default "";

    @AliasFor(annotation = Bean.class)
    String name() default "";

    String serverName() default "";

    Class<? extends ServerInterceptor>[] interceptors() default {};
}
