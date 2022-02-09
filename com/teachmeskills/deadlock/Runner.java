package com.teachmeskills.deadlock;

import com.teachmeskills.deadlock.model.thread.FirstThread;
import com.teachmeskills.deadlock.model.thread.SecondThread;

/**
 * Multithreading problem - deadlock.
 */

/*
 * To solve this problem: set the same synchronization order in threads
 * (lock-1 -> lock-2) or (lock-2 -> lock-1)
 */

public class Runner {
    public static final Object lock1 = new Object();
    public static final Object lock2 = new Object();

    public static void main(String[] args) {
        new FirstThread().start();
        new SecondThread().start();
    }
}