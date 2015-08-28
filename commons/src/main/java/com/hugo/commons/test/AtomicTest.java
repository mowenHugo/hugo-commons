package com.hugo.commons.test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author : hugo
 * @Date : 15/3/12 上午9:25.
 */
public class AtomicTest {

    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) {
        //以原子的方式递增  i++
        atomicInteger.getAndIncrement();
        //以原子的方式递增  ++i
        atomicInteger.incrementAndGet();
        System.out.println(atomicInteger);
    }
}