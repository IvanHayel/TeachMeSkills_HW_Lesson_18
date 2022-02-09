package com.teachmeskills.task_3.model.service;

import com.teachmeskills.task_3.model.car.Car;
import com.teachmeskills.task_3.model.station.ServiceStation;

import java.util.Random;

public class ReturnService extends Thread {
    private final ServiceStation STATION;
    private final Random GENERATOR;

    public ReturnService(ServiceStation station) {
        STATION = station;
        GENERATOR = new Random();
    }

    @Override
    public void run() {
        while (STATION.isNotReadyToClose()) {
            returnCar();
        }
        STATION.closeStation();
        STATION.showCloseInfo();
    }

    private void returnCar() {
        Car car = STATION.getRepairedCar();
        System.out.printf("[Car return service]: %s is transferring to owner.%n", car);
        sleepRandomTime();
        System.out.printf("[Car return service]: %s delivered successfully.%n", car);
    }

    private void sleepRandomTime() {
        try {
            Thread.sleep(GENERATOR.nextLong(5000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}