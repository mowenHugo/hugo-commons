package com.hugo.commons.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author : hugo
 * @Date : 15/3/8 下午3:22.
 */
public class TestThread {
    public static void main(String[] args) {
        //create thread pool,thread num is two.
        ExecutorService pool = Executors.newFixedThreadPool(2);

        Lock lock = new ReentrantLock(false);
        Runnable t1 = new MyRunnable("张三", 2000, lock);
        Runnable t2 = new MyRunnable("李四", 3500, lock);
        Runnable t3 = new MyRunnable("王五", 2700, lock);
        Runnable t4 = new MyRunnable("赵六", 4000, lock);
        Runnable t5 = new MyRunnable("王二", 5000, lock);
        Runnable t6 = new MyRunnable("李七", 2100, lock);
        Runnable t7 = new MyRunnable("王八", 2400, lock);

        pool.execute(t1);
        pool.execute(t2);
        pool.execute(t3);
        pool.execute(t4);
        pool.execute(t5);
        pool.execute(t6);
        pool.execute(t7);
        //shutdown pool
        pool.shutdown();
    }
}

class MyRunnable implements Runnable {
    /**
     * Java线程：新特征-原子量
     * 所谓的原子量即操作变量的操作是“原子的”，该操作不可再分，因此是线程安全的。
     * 为何要使用原子变量呢，原因是多个线程对单个变量操作也会引起一些问题。
     * 在Java5之前，可以通过volatile、synchronized关键字来解决并发访问的安全问题，但这样太麻烦。
     * Java5之后，专门提供了用来进行单变量多线程并发安全访问的工具包java.utils.concurrent.atomic，其中的类也很简单。
     * 这里要注意的一点是，原子量虽然可以保证单个变量在某一个操作过程的安全，但无法保证你整个代码块，或者整个程序的安全性。
     * 因此，通常还应该使用锁等同步机制来控制整个程序的安全性。
     * 有关原子量的用法很简单，关键是对原子量的认识，原子仅仅是保证变量操作的原子性，但整个程序还需要考虑线程安全的。
     * */

    /**
     * 关于类变量，方法区里有个静态区，静态区是专门用来存放静态变量以及静态块的。
     * 所有类的实例都共享方法区中的内容.
     */

    private static AtomicLong atomicLong = new AtomicLong(10000);

    private String name;
    private Integer x;
    private Lock lock;

    public MyRunnable(String name, Integer x, Lock lock) {
        this.name = name;
        this.x = x;
        this.lock = lock;
    }

    @Override
    public void run() {
        System.out.println(atomicLong.incrementAndGet());
//        lock.lock();
//        System.out.println(name + "执行了" + x + ", 当前余额：" + atomicLong.addAndGet(x));
//        lock.unlock();
    }
}

