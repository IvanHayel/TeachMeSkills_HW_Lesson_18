package com.teachmeskills.task_3.model.service;

import com.teachmeskills.task_3.model.car.Car;
import com.teachmeskills.task_3.model.station.ServiceStation;

import java.util.Random;

public class ReceiveService extends Thread {
    private final ServiceStation STATION;
    private final Random GENERATOR;

    public ReceiveService(ServiceStation station) {
        STATION = station;
        GENERATOR = new Random();
    }

    @Override
    public void run() {
        while (STATION.needCars()) {
            searchCar();
        }
    }

    private void searchCar() {
        System.out.println("[Car search service]: is looking for the next car.");
        sleepRandomTime();
        Car car = new Car();
        System.out.printf("[Car search service]: found - %s.%n", car);
        STATION.register(car);
    }

    private void sleepRandomTime() {
        try {
            Thread.sleep(1000 + GENERATOR.nextLong(5000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}