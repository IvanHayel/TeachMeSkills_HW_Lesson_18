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
    private final int maxCarsQuantity;
    private final ExecutorService executorService;
    private final ReceiveService receiveService;
    private final ReturnService returnService;
    private final Queue<Car> brokenCars;
    private final Queue<Car> repairedCars;
    private final List<Thread> workers;
    private final Object receiveLock;
    private final Object returnLock;

    private int carsReceived;
    private int carsReturned;

    public ServiceStation(int workersQuantity, int maxCarsQuantity) {
        executorService = Executors.newFixedThreadPool(workersQuantity, new WorkerThreadFactory());
        receiveService = new ReceiveService(this);
        returnService = new ReturnService(this);

        workers = new ArrayList<>(workersQuantity);
        for (int counter = 0; counter < workersQuantity; counter++) {
            workers.add(new Thread(new MaintenanceService(this)));
        }

        receiveLock = new Object();
        returnLock = new Object();

        brokenCars = new ArrayBlockingQueue<>(maxCarsQuantity);
        repairedCars = new ArrayBlockingQueue<>(maxCarsQuantity);
        this.maxCarsQuantity = maxCarsQuantity;

        carsReceived = 0;
        carsReturned = 0;
    }

    public void startWork() {
        receiveService.start();
        startRepairingCars();
        returnService.start();
    }

    private void startRepairingCars() {
        workers.forEach(executorService::execute);
    }

    public boolean needCars() {
        return carsReceived < maxCarsQuantity;
    }

    public boolean isNotReadyToClose() {
        return carsReturned < maxCarsQuantity;
    }

    public void register(Car car) {
        synchronized (receiveLock) {
            brokenCars.offer(car);
            carsReceived++;
            receiveLock.notifyAll();
        }
    }

    public synchronized void putRepairedCar(Car car) {
        repairedCars.offer(car);
        synchronized (returnLock) {
            returnLock.notifyAll();
        }
    }

    public Car getBrokenCar() {
        waitForBrokenCars();
        return brokenCars.poll();
    }

    private void waitForBrokenCars() {
        synchronized (receiveLock) {
            while (brokenCars.isEmpty() && isNotReadyToClose()) {
                try {
                    receiveLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
                if (!isNotReadyToClose()) {
                    executorService.shutdown();
                }
            }
        }
    }

    public Car getRepairedCar() {
        waitForRepairedCars();
        carsReturned++;
        return repairedCars.poll();
    }

    private void waitForRepairedCars() {
        synchronized (returnLock) {
            while (repairedCars.isEmpty()) {
                try {
                    returnLock.wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public void closeStation() {
        System.out.println("The workers went home...");
        synchronized (receiveLock) {
            receiveLock.notifyAll();
        }
    }

    public void showCloseInfo() {
        System.out.println("Station successfully finished work.");
        System.out.printf("Cars received and repaired: %d. Cars returned to owners: %d", carsReceived, carsReturned);
    }
}