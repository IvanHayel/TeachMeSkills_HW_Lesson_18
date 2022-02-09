package com.teachmeskills.task_2.model.runnable;

import java.util.concurrent.CountDownLatch;

public class MyRunnableImpl implements Runnable {
    private static final CountDownLatch latch = new CountDownLatch(3);

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        int priority = Thread.currentThread().getPriority();
        latch.countDown();
        if(latch.getCount() != 0){
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
        System.out.printf("%s with priority %d.%n", name, priority);
    }
}