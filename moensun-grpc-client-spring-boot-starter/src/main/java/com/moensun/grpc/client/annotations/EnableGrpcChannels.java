package com.moensun.grpc.client.annotations;

import com.moensun.grpc.client.GrpcChannelsRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(value = {GrpcChannelsRegister.class})
public @interface EnableGrpcChannels {
    String[] value() default {};

    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};
    Class<?>[] defaultConfiguration() default {};
    Class<?>[] channels() default {};
}
