package com.hugo.commons.test;

/**
 * @Author : hugo
 * @Date : 15/3/10 下午1:28.
 */
public class TestAbstractClass {

    private String name;

    public TestAbstractClass() {
    }

    public TestAbstractClass(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
    }

}

abstract class B extends TestAbstractClass {

    public B() {
        //super(); 每个类都有个默认的无参构造方法，并且在第一行隐式调用父类无参构造方法。
        //如果一个抽象类继承的父类没有明确的构造方法（构造方法私有化），那么这个抽象类不能继承该该父类。
    }

    public abstract void testB();
}



