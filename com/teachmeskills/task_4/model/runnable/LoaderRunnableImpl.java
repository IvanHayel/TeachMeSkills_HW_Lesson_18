package com.teachmeskills.task_4.model.runnable;

import com.teachmeskills.task_4.model.buffer.ReentrantLockBuffer;

public record LoaderRunnableImpl(ReentrantLockBuffer<Integer> buffer) implements Runnable {
    @Override
    public void run() {
        int toLoad = buffer.capacity();
        System.out.printf("[Loader]: i will load numbers from 1 to %d into buffer.%n", toLoad);
        for (Integer counter = 1; counter <= toLoad; counter++) {
            buffer.offer(counter);
        }
        System.out.println("[Loader]: i successfully load all numbers to buffer.");
    }
}