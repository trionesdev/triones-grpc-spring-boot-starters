package com.trionesdev.grpc.server;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
private final UserDAO userDAO;

    public void say(){
        userDAO.say();
    }
}
