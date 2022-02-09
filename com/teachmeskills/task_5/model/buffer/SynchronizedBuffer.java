package com.teachmeskills.task_5.model.buffer;

public class SynchronizedBuffer<T> {
    private final Object[] items;
    private final int capacity;
    private final Object emptyLock = new Object();
    private final Object fullLock = new Object();

    private int indexPoll;
    private int indexOffer;
    private int size;

    public SynchronizedBuffer(int capacity) {
        items = new Object[capacity];
        this.capacity = capacity;
    }

    public T poll() {
        if (size == 0) {
            waitForOffer();
        }
        try {
            synchronized (this) {
                Object item = items[indexPoll];
                items[indexPoll] = null;
                if (size > 1) {
                    indexPoll = ++indexPoll == capacity ? 0 : indexPoll;
                }
                size--;
                return (T) item;
            }
        } finally {
            synchronized (fullLock) {
                fullLock.notifyAll();
            }
        }
    }

    private void waitForOffer() {
        synchronized (emptyLock) {
            while (size < 1) {
                try {
                    emptyLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public void offer(T item) {
        if (size == capacity) {
            waitForPoll();
        }
        synchronized (this) {
            if (size != 0) {
                indexOffer = ++indexOffer == capacity ? 0 : indexOffer;
            }
            items[indexOffer] = item;
            size++;
        }
        synchronized (emptyLock) {
            emptyLock.notifyAll();
        }
    }


    private void waitForPoll() {
        synchronized (fullLock) {
            while (size == capacity) {
                try {
                    fullLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public int capacity() {
        return capacity;
    }
}