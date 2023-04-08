package com.moensun.grpc.client;

import com.moensun.grpc.client.annotations.GrpcClient;
import io.grpc.CallOptions;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Field;
import java.util.Objects;

public class GrpcClientBeanPostProcessor implements BeanPostProcessor, SmartLifecycle {
    private final GenericApplicationContext applicationContext;

    public GrpcClientBeanPostProcessor(GenericApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(beanName);
        Class<?> clazz = bean.getClass();
        for (Field field : clazz.getDeclaredFields() ) {
           GrpcClient grpcClient = AnnotationUtils.findAnnotation(field, GrpcClient.class);
           if(Objects.nonNull(grpcClient)){
               ManagedChannel channel = Grpc.newChannelBuilder("localhost:50051", InsecureChannelCredentials.create())        .build();
               if(applicationContext.getBeanNamesForType(field.getType()).length == 0){
                   applicationContext.registerBean(field.getType(),channel, CallOptions.DEFAULT);
               }
//               Object value = applicationContext.getBean(field.getType());
//               ReflectionUtils.makeAccessible(field);
//               ReflectionUtils.setField(field, bean, value);
           }
        }
        return bean;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
