package com.teachmeskills.data_race.model.runnable;

import com.teachmeskills.data_race.Runner;

public class MyRunnableImpl implements Runnable {
    @Override
    public void run() {
        for (int counter = 0; counter < 1000; counter++) {
            Runner.increment();
        }
    }
}