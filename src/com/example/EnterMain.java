package com.example;

import com.example.ioc.ApplicationContext;
import com.example.service.UserService;
import com.example.service.UserServiceImpl;

public class EnterMain {

    public static void main(String[] args){
        ApplicationContext ctx = new ApplicationContext(EnterMain.class);
        UserService userService = ctx.getBean(UserServiceImpl.class);
        userService.send();
    }
}
