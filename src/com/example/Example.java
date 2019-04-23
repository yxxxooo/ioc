package com.example;

public class Example {
    private static Example example = new Example();
    public static int count1;
    public static int count2 = 0;

    public Example() {
        count1++;
        count2++;
    }

    public static Example getExample(){
        return example;
    }
}
