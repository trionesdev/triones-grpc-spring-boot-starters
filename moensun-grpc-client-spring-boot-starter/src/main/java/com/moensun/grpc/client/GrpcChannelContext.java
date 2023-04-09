package com.moensun.grpc.client;

import io.grpc.Channel;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.cloud.context.named.NamedContextFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class GrpcChannelContext extends NamedContextFactory<GrpcChannelSpecification> {
    private Map<String, Channel> channelMap= new HashMap<>();
    public GrpcChannelContext() {
        super(GrpcChannelConfiguration.class, "grpc", "grpc.channel.name");
    }

    public void registerBeanDefinition(String name, String beanName, BeanDefinition beanDefinition) {
        AnnotationConfigApplicationContext context = this.getContext(name);
        context.registerBeanDefinition(beanName, beanDefinition);

    }

    public void registerChannel(String name, Channel channel) {
        channelMap.put(name,channel);
    }

    public Channel getChannel(String name){
        return channelMap.get(name);
    }
}
