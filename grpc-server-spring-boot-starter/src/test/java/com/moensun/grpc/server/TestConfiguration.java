package com.moensun.grpc.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class TestConfiguration {


    @Bean
    @Lazy
    public UserDAO userDAO(){
        return new UserDAO();
    }
}
