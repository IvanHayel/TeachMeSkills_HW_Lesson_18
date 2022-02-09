package com.teachmeskills.task_3.model.service;

import com.teachmeskills.task_3.model.car.Car;
import com.teachmeskills.task_3.model.station.ServiceStation;

import java.util.Random;

public class ReturnService extends Thread {
    private final ServiceStation station;
    private final Random generator;

    public ReturnService(ServiceStation station) {
        this.station = station;
        generator = new Random();
    }

    @Override
    public void run() {
        while (station.isNotReadyToClose()) {
            returnCar();
        }
        station.closeStation();
        station.showCloseInfo();
    }

    private void returnCar() {
        Car car = station.getRepairedCar();
        System.out.printf("[Car return service]: %s is transferring to owner.%n", car);
        sleepRandomTime();
        System.out.printf("[Car return service]: %s delivered successfully.%n", car);
    }

    private void sleepRandomTime() {
        try {
            Thread.sleep(generator.nextLong(5000));
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}