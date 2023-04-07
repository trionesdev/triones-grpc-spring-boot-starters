package com.moensun.grpc.server;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class GrpcServerTest {

    @Autowired
    private UserService userService;

    @Test
    public void start_test() throws InterruptedException {
        Thread.sleep(100000);
    }

    @Test
    public void say(){
        userService.say();
    }

}
