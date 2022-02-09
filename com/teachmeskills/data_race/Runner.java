package com.teachmeskills.data_race;

import com.teachmeskills.data_race.model.runnable.MyRunnableImpl;

/**
 * Multithreading problem - data race.
 */

public class Runner {
    volatile static int counter = 0;

    //public synchronized static void increment - solving data race problem
    public static void increment() {
        counter++;
    }

    public static void main(String[] args) {
        System.out.println("Start main thread.");

        MyRunnableImpl runnable = new MyRunnableImpl();

        Thread firstThread = new Thread(runnable);
        Thread secondThread = new Thread(runnable);

        firstThread.start();
        secondThread.start();

        try {
            firstThread.join();
            secondThread.join();
        } catch (InterruptedException e) {
            System.out.println("Interrupted catch.");
        }

        System.out.printf("Counter result value: %d/%d%n", counter, 2000);
        System.out.println("End main thread.");
    }
}