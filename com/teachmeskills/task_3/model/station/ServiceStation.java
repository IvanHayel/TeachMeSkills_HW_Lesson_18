package com.teachmeskills.task_3.model.station;

import com.teachmeskills.task_3.model.car.Car;
import com.teachmeskills.task_3.model.service.MaintenanceService;
import com.teachmeskills.task_3.model.service.ReceiveService;
import com.teachmeskills.task_3.model.service.ReturnService;
import com.teachmeskills.task_3.util.thread_factory.WorkerThreadFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServiceStation {
    private final int MAX_CARS_QUANTITY;
    private final ExecutorService EXECUTOR_SERVICE;
    private final ReceiveService RECEIVE_SERVICE;
    private final ReturnService RETURN_SERVICE;
    private final Queue<Car> BROKEN_CARS;
    private final Queue<Car> REPAIRED_CARS;
    private final List<Thread> WORKERS;
    private final Object RECEIVE_LOCK;
    private final Object RETURN_LOCK;

    private int carsReceived;
    private int carsReturned;

    public ServiceStation(int workersQuantity, int maxCarsQuantity) {
        EXECUTOR_SERVICE = Executors.newFixedThreadPool(workersQuantity, new WorkerThreadFactory());
        RECEIVE_SERVICE = new ReceiveService(this);
        RETURN_SERVICE = new ReturnService(this);

        WORKERS = new ArrayList<>(workersQuantity);
        for (int counter = 0; counter < workersQuantity; counter++) {
            WORKERS.add(new Thread(new MaintenanceService(this)));
        }

        RECEIVE_LOCK = new Object();
        RETURN_LOCK = new Object();

        BROKEN_CARS = new ArrayBlockingQueue<>(maxCarsQuantity);
        REPAIRED_CARS = new ArrayBlockingQueue<>(maxCarsQuantity);
        MAX_CARS_QUANTITY = maxCarsQuantity;

        carsReceived = 0;
        carsReturned = 0;
    }

    public void startWork() {
        RECEIVE_SERVICE.start();
        startRepairingCars();
        RETURN_SERVICE.start();
    }

    private void startRepairingCars() {
        WORKERS.forEach(EXECUTOR_SERVICE::execute);
    }

    public boolean needCars() {
        return carsReceived < MAX_CARS_QUANTITY;
    }

    public boolean isNotReadyToClose() {
        return carsReturned < MAX_CARS_QUANTITY;
    }

    public void register(Car car) {
        synchronized (RECEIVE_LOCK) {
            BROKEN_CARS.offer(car);
            carsReceived++;
            RECEIVE_LOCK.notifyAll();
        }
    }

    public synchronized void putRepairedCar(Car car) {
        REPAIRED_CARS.offer(car);
        synchronized (RETURN_LOCK) {
            RETURN_LOCK.notifyAll();
        }
    }

    public Car getBrokenCar() {
        waitForBrokenCars();
        return BROKEN_CARS.poll();
    }

    private void waitForBrokenCars() {
        synchronized (RECEIVE_LOCK) {
            while (BROKEN_CARS.isEmpty() && isNotReadyToClose()) {
                try {
                    RECEIVE_LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
                if (!isNotReadyToClose()) {
                    EXECUTOR_SERVICE.shutdown();
                }
            }
        }
    }

    public Car getRepairedCar() {
        waitForRepairedCars();
        carsReturned++;
        return REPAIRED_CARS.poll();
    }

    private void waitForRepairedCars() {
        synchronized (RETURN_LOCK) {
            while (REPAIRED_CARS.isEmpty()) {
                try {
                    RETURN_LOCK.wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public void closeStation() {
        System.out.println("The workers went home...");
        synchronized (RECEIVE_LOCK) {
            RECEIVE_LOCK.notifyAll();
        }
    }

    public void showCloseInfo() {
        System.out.println("Station successfully finished work.");
        System.out.printf("Cars received and repaired: %d. Cars returned to owners: %d", carsReceived, carsReturned);
    }
}