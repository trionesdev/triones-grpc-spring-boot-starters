package com.moensun.grpc.client;

import io.grpc.Channel;
import io.grpc.stub.AbstractBlockingStub;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GrpcStubFactoryBean implements FactoryBean<AbstractBlockingStub<?>>, InitializingBean,
        ApplicationContextAware, BeanFactoryAware {

    private Class<?> type;
    private Channel channel;
    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public AbstractBlockingStub<?> getObject() {
        return getTarget();
    }

    @Override
    public Class<AbstractBlockingStub<?>> getObjectType() {
        return null;
    }

    @Override
    public void afterPropertiesSet() {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }

    public AbstractBlockingStub<?> getTarget() {
        try {
            Method newBlockingStub = type.getMethod("newBlockingStub", Channel.class);
            return (AbstractBlockingStub<?>) newBlockingStub.invoke(null, channel);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
