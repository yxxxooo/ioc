package com.example.ioc;

public class IOCClassload extends ClassLoader{

    public Class<?> defineMyClass(String name, byte[] b, int off, int len) {
        return super.defineClass(name, b ,off ,len );
    }
}
