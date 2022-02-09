package com.teachmeskills.task_3;

import com.teachmeskills.task_3.model.station.ServiceStation;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * There is a service station. A service station may have a certain number of vehicles in service.
 * Create a class that will run in a separate thread and will add cars for servicing in the service station.
 * Create a class that will run in a separate thread and will pick up fixed cars from the service station.
 * Let the maximum number of machines available for servicing be set through the property file.
 * Use synchronized, wait, notify.
 */

public class Runner {
    private static final String PROPERTY_PATH =
            "src/com/teachmeskills/task_3/resources/app.properties";
    private static final Properties PROPS = new Properties();
    private static final int WORKERS_QUANTITY;
    private static final int MAX_CARS_QUANTITY;

    static {
        loadProperties();
        WORKERS_QUANTITY = Integer.parseInt(PROPS.getProperty("workers.quantity"));
        MAX_CARS_QUANTITY = Integer.parseInt(PROPS.getProperty("maximum.cars.quantity"));
    }

    public static void main(String[] args) {
        ServiceStation station = new ServiceStation(WORKERS_QUANTITY, MAX_CARS_QUANTITY);
        station.startWork();
    }

    private static void loadProperties() {
        try (FileInputStream propertiesFile = new FileInputStream(PROPERTY_PATH)) {
            PROPS.load(propertiesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}