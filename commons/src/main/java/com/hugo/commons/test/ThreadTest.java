package com.hugo.commons.test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author : Hugo.Wwg
 * @Date : 15/4/5 下午3:36.
 */
public class ThreadTest {

    public static Integer resource = 0;

    public static Lock produceLock = new ReentrantLock();
    public static Lock consumeLock = new ReentrantLock();

    public static void produce(){
        ThreadTest.consume();
        System.out.println("produce " + Thread.currentThread() + ++ThreadTest.resource + "   " + ThreadTest.resource);
    }

    public static void consume(){
        ThreadTest.produce();
        System.out.println("Consume " + Thread.currentThread() + --ThreadTest.resource + "   " + ThreadTest.resource);
    }


    public static void main(String[] args) {

        for (int i = 0; i < 1; i++) {
            new Thread(new Produce()).start();
            new Thread(new Consume()).start();
        }

    }

}

class Produce implements Runnable {
    @Override
    public void run() {
        ThreadTest.produce();
    }
}

class Consume implements Runnable {

    @Override
    public void run() {
        ThreadTest.consume();
    }
}

