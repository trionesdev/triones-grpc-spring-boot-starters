package com.moensun.grpc.client;

import com.moensun.grpc.client.annotations.EnableGrpcChannels;
import com.moensun.grpc.client.annotations.GrpcChannel;
import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.*;
import org.springframework.beans.factory.support.*;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.*;

public class GrpcChannelsRegister implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware {
    private ResourceLoader resourceLoader;

    private Environment environment;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        registerDefaultConfiguration(metadata, registry);
        registerGrpcChannels(metadata, registry);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


    private void registerDefaultConfiguration(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        Map<String, Object> defaultAttrs = metadata.getAnnotationAttributes(EnableGrpcChannels.class.getName(), true);

        if (defaultAttrs != null && defaultAttrs.containsKey("defaultConfiguration")) {
            String name;
            if (metadata.hasEnclosingClass()) {
                name = "default." + metadata.getEnclosingClassName();
            } else {
                name = "default." + metadata.getClassName();
            }
            registerChannelConfiguration(registry, name, defaultAttrs.get("defaultConfiguration"));
        }
    }

    static String getName(String name) {
        if (!StringUtils.hasText(name)) {
            return "";
        }
        return name;
    }


    public void registerGrpcChannels(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        LinkedHashSet<BeanDefinition> candidateComponents = new LinkedHashSet<>();
        Map<String, Object> attrs = metadata.getAnnotationAttributes(EnableGrpcChannels.class.getName());
        final Class<?>[] stubs = attrs == null ? null : (Class<?>[]) attrs.get("channels");
        if (stubs == null || stubs.length == 0) {
            ClassPathScanningCandidateComponentProvider scanner = getScanner();
            scanner.setResourceLoader(this.resourceLoader);
            scanner.addIncludeFilter(new AnnotationTypeFilter(GrpcChannel.class));
            Set<String> basePackages = getBasePackages(metadata);
            for (String basePackage : basePackages) {
                candidateComponents.addAll(scanner.findCandidateComponents(basePackage));
            }
        } else {
            for (Class<?> clazz : stubs) {
                candidateComponents.add(new AnnotatedGenericBeanDefinition(clazz));
            }
        }

        for (BeanDefinition candidateComponent : candidateComponents) {
            if (candidateComponent instanceof AnnotatedBeanDefinition) {
                AnnotatedBeanDefinition beanDefinition = (AnnotatedBeanDefinition) candidateComponent;
                AnnotationMetadata annotationMetadata = beanDefinition.getMetadata();
                Map<String, Object> attributes = annotationMetadata
                        .getAnnotationAttributes(GrpcChannel.class.getCanonicalName());
                String name = getChannelName(attributes);
                registerChannelConfiguration(registry, name, attributes.get("configuration"));
                registerGrpcChannel(registry, annotationMetadata, attributes);

//                AbstractBeanDefinition.
            }
        }

    }

    private void registerChannelConfiguration(BeanDefinitionRegistry registry, Object name, Object configuration) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(GrpcChannelSpecification.class);
        builder.addConstructorArgValue(name);
        builder.addConstructorArgValue(configuration);
        registry.registerBeanDefinition(name + "." + GrpcChannelSpecification.class.getSimpleName(),
                builder.getBeanDefinition());
    }

    private void registerGrpcChannel(BeanDefinitionRegistry registry, AnnotationMetadata annotationMetadata,
                                     Map<String, Object> attributes) {
        String className = annotationMetadata.getClassName();
        ConfigurableBeanFactory beanFactory = registry instanceof ConfigurableBeanFactory
                ? (ConfigurableBeanFactory) registry : null;
        Class<?> clazz = ClassUtils.resolveClassName(className, null);
//        String name = getName(beanFactory,attributes);
//        GrpcChannelFactoryBean factoryBean = new GrpcChannelFactoryBean();
//        factoryBean.setName(name);
//        factoryBean.setType(Channel.class);
//        factoryBean.setBeanFactory(beanFactory);
//        factoryBean.setTarget("localhost:50051");
//        BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(Channel.class, () -> {
//            return factoryBean.getObject();
//        });
//        AbstractBeanDefinition beanDefinition = definition.getBeanDefinition();
//        definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
//        definition.setLazyInit(true);
//
//        BeanDefinitionHolder holder = new BeanDefinitionHolder(beanDefinition, className, null);
//        BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);


       ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:50051").build();

        GrpcChannel grpcChannel = AnnotationUtils.findAnnotation(clazz, GrpcChannel.class);
        Arrays.stream(grpcChannel.stubs()).forEach(stubClass->{
            System.out.println("ss");
//            BeanDefinitionBuilder stubBeanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(stubClass);
//            AbstractBeanDefinition stubBeanDefinition = stubBeanDefinitionBuilder.getBeanDefinition();
//            GenericBeanDefinition stubBeanDefinition = new GenericBeanDefinition();
//            stubBeanDefinition.setBeanClass(stubClass);
//            stubBeanDefinition.setAttribute("channel",channel);
//            stubBeanDefinition.setAttribute("callOptions", CallOptions.DEFAULT);
//            stubBeanDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
//            stubBeanDefinition.setLazyInit(true);
//            BeanDefinitionHolder stubHolder = new BeanDefinitionHolder(stubBeanDefinition, stubClass.getSimpleName(), null);
//            BeanDefinitionReaderUtils.registerBeanDefinition(stubHolder, registry);

        });
    }

    protected ClassPathScanningCandidateComponentProvider getScanner() {
        return new ClassPathScanningCandidateComponentProvider(false, this.environment) {
            @Override
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                boolean isCandidate = false;
                if (beanDefinition.getMetadata().isIndependent()) {
                    if (!beanDefinition.getMetadata().isAnnotation()) {
                        isCandidate = true;
                    }
                }
                return isCandidate;
            }
        };
    }

    protected Set<String> getBasePackages(AnnotationMetadata importingClassMetadata) {
        Map<String, Object> attributes = importingClassMetadata
                .getAnnotationAttributes(EnableGrpcChannels.class.getCanonicalName());

        Set<String> basePackages = new HashSet<>();
        for (String pkg : (String[]) attributes.get("value")) {
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }
        for (String pkg : (String[]) attributes.get("basePackages")) {
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }
        for (Class<?> clazz : (Class[]) attributes.get("basePackageClasses")) {
            basePackages.add(ClassUtils.getPackageName(clazz));
        }

        if (basePackages.isEmpty()) {
            basePackages.add(ClassUtils.getPackageName(importingClassMetadata.getClassName()));
        }
        return basePackages;
    }

    private String resolve(ConfigurableBeanFactory beanFactory, String value) {
        if (StringUtils.hasText(value)) {
            if (beanFactory == null) {
                return this.environment.resolvePlaceholders(value);
            }
            BeanExpressionResolver resolver = beanFactory.getBeanExpressionResolver();
            String resolved = beanFactory.resolveEmbeddedValue(value);
            if (resolver == null) {
                return resolved;
            }
            Object evaluateValue = resolver.evaluate(resolved, new BeanExpressionContext(beanFactory, null));
            if (evaluateValue != null) {
                return String.valueOf(evaluateValue);
            }
            return null;
        }
        return value;
    }

    String getName(ConfigurableBeanFactory beanFactory, Map<String, Object> attributes) {
        String name = (String) attributes.get("serviceId");
        if (!StringUtils.hasText(name)) {
            name = (String) attributes.get("name");
        }
        if (!StringUtils.hasText(name)) {
            name = (String) attributes.get("value");
        }
        name = resolve(beanFactory, name);
        return getName(name);
    }

    private String getChannelName(Map<String, Object> client) {
        if (client == null) {
            return null;
        }
        String value = (String) client.get("value");
        if (!StringUtils.hasText(value)) {
            value = (String) client.get("name");
        }
        if (!StringUtils.hasText(value)) {
            value = (String) client.get("serviceId");
        }
        if (StringUtils.hasText(value)) {
            return value;
        }

        throw new IllegalStateException("Either 'name' or 'value' must be provided in @"
                + GrpcChannel.class.getSimpleName());
    }

}
