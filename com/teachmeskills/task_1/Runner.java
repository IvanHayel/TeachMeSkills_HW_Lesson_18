package com.teachmeskills.task_1;

import com.teachmeskills.task_1.model.runnable.MyRunnableImpl;

/**
 * Create three streams T1, T2 and T3.
 * Implement the execution of the thread in the sequence T3 -> T2 -> T1.
 */

public class Runner {
    public static void main(String[] args) throws InterruptedException {
        MyRunnableImpl runnable = new MyRunnableImpl();

        Thread firstThread = new Thread(runnable,"First Thread");
        Thread secondThread = new Thread(runnable,"Second Thread");
        Thread thirdThread = new Thread(runnable,"Third Thread");

        thirdThread.start();
        thirdThread.join();
        secondThread.start();
        secondThread.join();
        firstThread.start();
    }
}