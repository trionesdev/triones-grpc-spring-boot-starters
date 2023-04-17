package com.moensun.grpc.client.annotations;

import io.grpc.stub.AbstractBlockingStub;
import io.grpc.stub.AbstractStub;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface GrpcChannel {
    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

    String serverName() default "";

    Class<?>[] configuration() default {};

    Class<? extends AbstractStub>[] stubs() default {};
}
