package com.spring.module.async.threadpool;

import com.spring.module.async.model.AsyncFutureTask;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: hujingtian
 * @Date: 2019/7/5 17:32
 * @Description:
 */
public class AsyncThreadPool {

    private AsyncThreadPool() {

    }


    private static int CORE_POOL_SIZE = 10;
    private static int MAX_POOL_SIZE = 100;
    private static int KEEP_ALIVE_TIME = 10000;
    private static BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(10);
    private static ThreadFactory threadFactory = new ThreadFactory() {
        /**
         * Constructs a new {@code Thread}.  Implementations may also initialize
         * priority, name, daemon status, {@code ThreadGroup}, etc.
         *
         * @param r a runnable to be executed by new thread instance
         * @return constructed thread, or {@code null} if the request to
         * create a thread is rejected
         */
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r,"AsyncThreadPool:"+integer.getAndIncrement());
        }

        private final AtomicInteger integer = new AtomicInteger();

    };

    private static AsyncThreadPoolExecutor threadPool;
    static {
        threadPool = new AsyncThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME,
                TimeUnit.SECONDS, workQueue, threadFactory);
    }

    public static Future submit(AsyncFutureTask futureTask){
        return threadPool.submit(futureTask);
    }

}
