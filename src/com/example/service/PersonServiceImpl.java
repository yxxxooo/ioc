package com.example.service;

import com.example.ioc.beanenum.AutoWiter;
import com.example.ioc.beanenum.Service;

@Service
public class PersonServiceImpl implements PersonService{

    @AutoWiter
    private UserService userService;

    @Override
    public void execute() {
        System.out.println("IOC DI ："+userService);
        userService.send();
    }
}
