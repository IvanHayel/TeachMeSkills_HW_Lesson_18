package com.teachmeskills.task_4.model.buffer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockBuffer<T> {
    private final Object[] items;
    private final int capacity;
    private final Lock lock = new ReentrantLock();
    private final Condition empty = lock.newCondition();
    private final Condition full = lock.newCondition();

    private int indexPoll;
    private int indexOffer;
    private int size;

    public ReentrantLockBuffer(int capacity) {
        items = new Object[capacity];
        this.capacity = capacity;
    }

    public T poll() {
        try {
            lock.lockInterruptibly();
            if (size == 0) {
                waitForOffer();
            }
            Object item = items[indexPoll];
            items[indexPoll] = null;
            if (size > 1) {
                indexPoll = ++indexPoll == capacity ? 0 : indexPoll;
            }
            full.signalAll();
            size--;
            return (T) item;
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
            throw new IllegalStateException();
        } finally {
            lock.unlock();
        }
    }

    private void waitForOffer() {
        while (size < 1) {
            try {
                empty.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    public void offer(T item) {
        try {
            lock.lockInterruptibly();
            if (size == capacity) {
                waitForPoll();
            }
            if (size != 0) {
                indexOffer = ++indexOffer == capacity ? 0 : indexOffer;
            }
            items[indexOffer] = item;
            size++;
            empty.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
            throw new IllegalStateException();
        } finally {
            lock.unlock();
        }
    }

    private void waitForPoll() {
        while (size == capacity) {
            try {
                full.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    public int capacity() {
        return capacity;
    }
}