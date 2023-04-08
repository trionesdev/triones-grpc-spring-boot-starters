package com.moensun.grpc.client;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

@Configuration
public class GrpcStubConfiguration {


    @Configuration
    @Import(value = {AutoConfiguredRegistrar.class,GrpcRegister.class})
    public static class AutoConfiguredRegistrarConfiguration implements InitializingBean {
        @Override
        public void afterPropertiesSet() throws Exception {

        }
    }

    public static class AutoConfiguredRegistrar implements EnvironmentAware, BeanFactoryPostProcessor {

        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        }

        @Override
        public void setEnvironment(Environment environment) {

        }
    }

}
