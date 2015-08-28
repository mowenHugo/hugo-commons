package com.hugo.commons.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * @Author : Hugo.Wwg
 * @Date : 15/8/28 下午4:57.
 */
public class TestCountDownLatch {

    /**
     * CountDownLatch，一个同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。
     *
     * 主要方法: public CountDownLatch(int count); public void countDown(); public void await() throws
     * InterruptedException
     *
     * 构造方法参数指定了计数的次数 countDown方法，当前线程调用此方法，则计数减一 await方法，调用此方法会一直阻塞当前线程，直到计时器的值为0
     */


    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2); //两个工人的协作
        Worker worker1 = new Worker("user1", 5000 , latch);
        Worker worker2 = new Worker("user2", 8000, latch);
        worker1.start();
        worker2.start();
        latch.await(); //等待user1和user2完成工作,
        System.out.println("user1 and user2 work down at " + sdf.format(new Date()));
        Worker worker3 = new Worker("user3", 6000, latch);
        worker3.start();
        System.out.println("main thread work down at " + sdf.format(new Date()));
    }


    static class Worker extends Thread{
        String workerName;
        int workerTime;
        CountDownLatch countDownLatch;
        public Worker(String workerName, int workerTime, CountDownLatch countDownLatch){
            this.workerName = workerName;
            this.workerTime = workerTime;
            this.countDownLatch = countDownLatch;
        }

        public void run(){
            System.out.println("Worker " + workerName + " do work begin at " + sdf.format(new Date()));
            doWork(); //开始工作
            System.out.println("Worker " + workerName + " do work complete at " + sdf.format(new Date()));
            countDownLatch.countDown(); //工人完成工作，计数器减一
        }

        private void doWork(){
            try{
                Thread.sleep(workerTime);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

}
