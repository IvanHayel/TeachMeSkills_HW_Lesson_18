package com.teachmeskills.task_3.model.service;

import com.teachmeskills.task_3.model.car.Car;
import com.teachmeskills.task_3.model.station.ServiceStation;

import java.util.Random;

public class MaintenanceService implements Runnable {
    private final ServiceStation station;
    private final Random generator;

    public MaintenanceService(ServiceStation station) {
        this.station = station;
        generator = new Random();
    }

    @Override
    public void run() {
        String worker = Thread.currentThread().getName();
        while (station.isNotReadyToClose()) {
            repairCar(worker);
        }
    }

    private void repairCar(String worker) {
        Car car = station.getBrokenCar();
        if (car != null) {
            System.out.printf("[%s]: start repairing %s.%n", worker, car);
            sleepRandomTime();
            station.putRepairedCar(car);
            System.out.printf("[%s]: %s repaired.%n", worker, car);
        }
    }

    private void sleepRandomTime() {
        try {
            Thread.sleep(3000 + generator.nextLong(5000));
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}