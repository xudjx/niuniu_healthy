package com.niuniu.motion.common.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingletonThreadPool {

    private static final int DEFAULT_THREAD_POOL_SIZE = 50;

    private static ExecutorService executorService;

    private SingletonThreadPool() {

    }

    public static ExecutorService getExecutorService() {
        if (executorService == null) {
            synchronized (SingletonThreadPool.class) {
                if (executorService == null) {
                    executorService = Executors.newFixedThreadPool(DEFAULT_THREAD_POOL_SIZE);
                }
            }
        }
        return executorService;
    }
}
