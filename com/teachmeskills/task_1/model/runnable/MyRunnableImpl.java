package com.teachmeskills.task_1.model.runnable;

public class MyRunnableImpl implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
}