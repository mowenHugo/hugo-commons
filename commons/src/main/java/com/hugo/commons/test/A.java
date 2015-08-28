package com.hugo.commons.test;

/**
 * @Author : hugo
 * @Date : 15/3/4 下午4:06.
 */
public class A {

    private String name;
    private Integer age;
    private String sex;

    public A() {
    }

    public A(Integer age) {
        this.age = age;
    }

    public void print(String message) {
        System.out.println(message);
    }
}

