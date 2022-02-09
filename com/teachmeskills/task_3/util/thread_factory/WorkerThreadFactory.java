package com.teachmeskills.task_3.util.thread_factory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class WorkerThreadFactory implements ThreadFactory {
    private static final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Thread newThread(Runnable task) {
        String prefix = "Worker #";
        return new Thread(task, prefix + counter.incrementAndGet());
    }
}