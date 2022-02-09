package com.teachmeskills.deadlock.model.thread;

import com.teachmeskills.deadlock.Runner;

public class FirstThread extends Thread {
    @Override
    public void run() {
        System.out.println("FirstThread: attempt to capture monitor of lock-2");
        synchronized (Runner.lock2) {
            System.out.println("FirstThread: monitor of lock-2 captured");
            System.out.println("FirstThread: attempt to capture monitor of lock-1");
            synchronized (Runner.lock1) {
                System.out.println("FirstThread: monitor of lock-1 and lock-2 captured");
            }
        }
    }
}