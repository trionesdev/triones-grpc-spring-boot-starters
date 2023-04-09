package com.moensun.grpc.client;

import io.grpc.Channel;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

public class GrpcChannelFactoryBean implements FactoryBean<Channel>, InitializingBean,
        ApplicationContextAware, BeanFactoryAware {
    private Class<?> type;
    private String name;
    private String target;
    private BeanFactory beanFactory;

    private ApplicationContext applicationContext;

    @Override
    public Channel getObject() {
        return getTarget();
    }

    @Override
    public Class<?> getObjectType() {
        return this.type;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.hasText(this.name, "Name must be set");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    protected ManagedChannelBuilder<?> grpcChannel(GrpcChannelContext context) {
        ManagedChannelBuilder<?> builder = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create())
                .usePlaintext();
        return builder;
    }

    protected Channel getTarget() {
        GrpcChannelContext context = beanFactory != null ? beanFactory.getBean(GrpcChannelContext.class)
                : applicationContext.getBean(GrpcChannelContext.class);
//        GrpcChannelContext contextntext = applicationContext.getBean(GrpcChannelContext.class);
        ManagedChannelBuilder<?> builder = grpcChannel(context);

        return builder.build();
    }


    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
