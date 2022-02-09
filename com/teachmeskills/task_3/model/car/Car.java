package com.teachmeskills.task_3.model.car;

public class Car {
    private static int nextId = 0;

    private final int identifier;

    public Car() {
        identifier = ++nextId;
    }

    @Override
    public String toString() {
        return "Car #" + identifier;
    }
}