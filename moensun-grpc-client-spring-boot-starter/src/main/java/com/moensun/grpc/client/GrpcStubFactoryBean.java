package com.moensun.grpc.client;

import io.grpc.Channel;
import io.grpc.stub.AbstractBlockingStub;
import io.grpc.stub.AbstractFutureStub;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;

public class GrpcStubFactoryBean implements FactoryBean<Object>, InitializingBean,
        ApplicationContextAware, BeanFactoryAware {


    private Class<?> type;
    private String name;
    private String channelBeanName;
    private BeanFactory beanFactory;
    private ApplicationContext applicationContext;

    public void setChannelBeanName(String channelBeanName) {
        this.channelBeanName = channelBeanName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object getObject() {
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
        this.applicationContext = applicationContext;
    }

    public Object getTarget() {

        try {
            Method newStubMethod;
            if (AbstractBlockingStub.class.isAssignableFrom(type)) {
                newStubMethod = type.getEnclosingClass().getMethod("newBlockingStub", Channel.class);
            } else if (AbstractFutureStub.class.isAssignableFrom(type)) {
                newStubMethod = type.getEnclosingClass().getMethod("newFutureStub", Channel.class);
            } else {
                newStubMethod = type.getEnclosingClass().getMethod("newStub", Channel.class);
            }
            return newStubMethod.invoke(null, beanFactory.getBean(channelBeanName, Channel.class));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
