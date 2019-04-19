package com.example.service;

import com.example.entity.Person;
import com.example.entity.User;
import com.example.ioc.beanenum.AutoWiter;
import com.example.ioc.beanenum.Service;

@Service
public class UserServiceImpl implements UserService{

    @AutoWiter
    private Person person;
    @AutoWiter
    private User user;

    @Override
    public void send() {
        System.out.println("IOC DI ："+person);
        System.out.println("IOC DI ："+user);
    }
}
