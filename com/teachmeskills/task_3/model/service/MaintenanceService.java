package com.teachmeskills.task_3.model.service;

import com.teachmeskills.task_3.model.car.Car;
import com.teachmeskills.task_3.model.station.ServiceStation;

import java.util.Random;

public class MaintenanceService implements Runnable {
    private final ServiceStation STATION;
    private final Random GENERATOR;

    public MaintenanceService(ServiceStation station) {
        this.STATION = station;
        GENERATOR = new Random();
    }

    @Override
    public void run() {
        String worker = Thread.currentThread().getName();
        while (STATION.isNotReadyToClose()) {
            repairCar(worker);
        }
    }

    private void repairCar(String worker) {
        Car car = STATION.getBrokenCar();
        if (car != null) {
            System.out.printf("[%s]: start repairing %s.%n", worker, car);
            sleepRandomTime();
            STATION.putRepairedCar(car);
            System.out.printf("[%s]: %s repaired.%n", worker, car);
        }
    }

    private void sleepRandomTime() {
        try {
            Thread.sleep(3000 + GENERATOR.nextLong(5000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}