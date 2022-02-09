package com.teachmeskills.task_2;

import com.teachmeskills.task_2.model.runnable.MyRunnableImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Set a name and priority for each thread from the config file.
 * Let the name and priority of the thread be set through the property.
 * Let there be 3 streams.
 * Create and run 3 threads.
 */

public class Runner {
    private static final String PROPERTY_PATH =
            "src/com/teachmeskills/task_2/resources/app.properties";
    private static final Properties PROPS = new Properties();

    static {
        loadProperties();
    }

    public static void main(String[] args) {
        List<Thread> morning = new ArrayList<>();

        Thread newsThread = new Thread(new MyRunnableImpl());
        String newsName = PROPS.getProperty("thread.news.name");
        int newsPriority = Integer.parseInt(PROPS.getProperty("thread.news.priority"));
        newsThread.setName(newsName);
        newsThread.setPriority(newsPriority);
        morning.add(newsThread);

        Thread breakfastThread = new Thread(new MyRunnableImpl());
        String breakfastName = PROPS.getProperty("thread.breakfast.name");
        int breakfastPriority = Integer.parseInt(PROPS.getProperty("thread.breakfast.priority"));
        breakfastThread.setName(breakfastName);
        breakfastThread.setPriority(breakfastPriority);
        morning.add(breakfastThread);

        Thread coffeeThread = new Thread(new MyRunnableImpl());
        String coffeeName = PROPS.getProperty("thread.coffee.name");
        int coffeePriority = Integer.parseInt(PROPS.getProperty("thread.coffee.priority"));
        coffeeThread.setName(coffeeName);
        coffeeThread.setPriority(coffeePriority);
        morning.add(coffeeThread);

        morning.forEach(Thread::start);
    }

    private static void loadProperties() {
        try (FileInputStream propertiesFile = new FileInputStream(PROPERTY_PATH)) {
            PROPS.load(propertiesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}