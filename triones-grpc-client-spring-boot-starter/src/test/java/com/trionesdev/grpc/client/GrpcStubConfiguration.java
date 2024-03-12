package com.trionesdev.grpc.client;

import io.opentelemetry.example.grpc.GreeterGrpc;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcStubConfiguration {


//    @Bean
//    public GreeterGrpc.GreeterBlockingStub greeterBlockingStub(Channel channel){
//        return GreeterGrpc.newBlockingStub(channel);
//    }

//    @Configuration
//    @Import(value = {AutoConfiguredRegistrar.class,GrpcRegister.class})
//    public static class AutoConfiguredRegistrarConfiguration implements InitializingBean {
//        @Override
//        public void afterPropertiesSet() throws Exception {
//
//        }
//    }
//
//    public static class AutoConfiguredRegistrar implements EnvironmentAware, BeanFactoryPostProcessor {
//
//        @Override
//        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
//
//        }
//
//        @Override
//        public void setEnvironment(Environment environment) {
//
//        }
//    }

}
