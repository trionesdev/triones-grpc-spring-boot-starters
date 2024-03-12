package com.trionesdev.grpc.client;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.cloud.context.named.NamedContextFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class GrpcChannelContext extends NamedContextFactory<GrpcChannelSpecification> {
    public GrpcChannelContext() {
        super(GrpcChannelConfiguration.class, "grpc", "grpc.channel.name");
    }

    public void registerBeanDefinition(String name, String beanName, BeanDefinition beanDefinition) {
        AnnotationConfigApplicationContext context = this.getContext(name);
        context.registerBeanDefinition(beanName, beanDefinition);

    }

}
