package com.example;

import com.example.ioc.ApplicationContext;
import com.example.service.PersonService;
import com.example.service.PersonServiceImpl;

public class EnterMain {

    public static void main(String[] args) throws Exception{
        ApplicationContext ctx = new ApplicationContext(EnterMain.class);
        PersonService personService = ctx.getBean(PersonServiceImpl.class);
        personService.execute();


    }
}
