package com.teachmeskills.task_4.model.runnable;

import com.teachmeskills.task_4.model.buffer.ReentrantLockBuffer;

import java.util.ArrayList;
import java.util.List;

public record UploaderRunnableImpl(ReentrantLockBuffer<Integer> buffer) implements Runnable {
    @Override
    public void run() {
        List<Integer> numbers = new ArrayList<>();
        System.out.println("[Uploader]: i will upload all numbers from buffer.");
        for (int counter = 0; counter < buffer.capacity(); counter++) {
            numbers.add(buffer.poll());
        }
        System.out.printf("[Uploader]: i successfully upload %d numbers: %s%n", numbers.size(), numbers);
    }
}
