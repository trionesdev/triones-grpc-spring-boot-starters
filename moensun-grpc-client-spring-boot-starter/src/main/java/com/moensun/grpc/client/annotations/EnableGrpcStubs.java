package com.moensun.grpc.client.annotations;

import com.moensun.grpc.client.GrpcStubRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(value = {GrpcStubRegister.class})
public @interface EnableGrpcStubs {
    String[] value() default {};

    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};
    Class<?>[] defaultConfiguration() default {};
    Class<?>[] stubs() default {};
}
