# TeachMeSkills homework Lesson 18

--- 

## Table of contents

### 1. [Multithreading tasks](https://github.com/IvanHayel/TeachMeSkills_HW_Lesson_18#multithreading-tasks)
### 2. [Multithreading problems](https://github.com/IvanHayel/TeachMeSkills_HW_Lesson_18#multithreading-problems)

--- 

### Multithreading tasks

| **№** | **TASK**                                                                                                                                                                                                                                                                                                                                                                                                                                             |                                             **SOLUTION**                                              |
|:-----:|:-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|:-----------------------------------------------------------------------------------------------------:|
| **1** | Create three streams `T1`, `T2` and `T3`. Implement the execution of the thread in the sequence `T3 -> T2 -> T1`.                                                                                                                                                                                                                                                                                                                                    | [click](https://github.com/IvanHayel/TeachMeSkills_HW_Lesson_18/tree/master/com/teachmeskills/task_1) |
| **2** | Set a name and priority for each thread from the config file. Let the name and priority of the thread be set through the property. Let there be 3 streams. Create and run 3 threads.                                                                                                                                                                                                                                                                 | [click](https://github.com/IvanHayel/TeachMeSkills_HW_Lesson_18/tree/master/com/teachmeskills/task_2) |
| **3** | There is a service station. A service station may have a certain number of vehicles in service. Create a class that will run in a separate thread and will add cars for servicing in the service station. Create a class that will run in a separate thread and will pick up fixed cars from the service station. Let the maximum number of machines available for servicing be set through the property file. Use `synchronized`, `wait`, `notify`. | [click](https://github.com/IvanHayel/TeachMeSkills_HW_Lesson_18/tree/master/com/teachmeskills/task_3) |
| **4** | Write a multithreaded bounded buffer using `ReentrantLock`.                                                                                                                                                                                                                                                                                                                                                                                          | [click](https://github.com/IvanHayel/TeachMeSkills_HW_Lesson_18/tree/master/com/teachmeskills/task_4) |
| **5** | Write a multithreaded bounded buffer using `synchronized`.                                                                                                                                                                                                                                                                                                                                                                                           | [click](https://github.com/IvanHayel/TeachMeSkills_HW_Lesson_18/tree/master/com/teachmeskills/task_5) |

--- 

### Multithreading problems

| **MULTITHREADING PROBLEM** |                                              **SIMULATION**                                              |
|:--------------------------:|:--------------------------------------------------------------------------------------------------------:|
|       **DATA RACE**        | [click](https://github.com/IvanHayel/TeachMeSkills_HW_Lesson_18/tree/master/com/teachmeskills/data_race) |
|        **DEADLOCK**        | [click](https://github.com/IvanHayel/TeachMeSkills_HW_Lesson_18/tree/master/com/teachmeskills/deadlock)  |

--- 
