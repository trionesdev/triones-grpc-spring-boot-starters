package com.moensun.grpc.server.annotations;

import io.grpc.ServerInterceptor;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface GrpcService {
    @AliasFor(annotation = Component.class)
    String value() default "";

    Class<? extends ServerInterceptor>[] interceptors() default {};
}
