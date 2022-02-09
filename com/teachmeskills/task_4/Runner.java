package com.teachmeskills.task_4;

import com.teachmeskills.task_4.model.buffer.ReentrantLockBuffer;
import com.teachmeskills.task_4.model.runnable.LoaderRunnableImpl;
import com.teachmeskills.task_4.model.runnable.UploaderRunnableImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Write a multithreaded bounded buffer using ReentrantLock.
 */

public class Runner {
    private static final String PROPERTY_PATH =
            "src/com/teachmeskills/task_4/resources/app.properties";
    private static final Properties PROPS = new Properties();
    private static final int BUFFER_CAPACITY;

    static {
        loadProperties();
        BUFFER_CAPACITY = Integer.parseInt(PROPS.getProperty("buffer.capacity"));
    }

    public static void main(String[] args) {
        ReentrantLockBuffer<Integer> buffer = new ReentrantLockBuffer<>(BUFFER_CAPACITY);
        new Thread(new LoaderRunnableImpl(buffer)).start();
        new Thread(new UploaderRunnableImpl(buffer)).start();
    }

    private static void loadProperties() {
        try (FileInputStream propertiesFile = new FileInputStream(PROPERTY_PATH)) {
            PROPS.load(propertiesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}