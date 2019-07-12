package com.spring.module.async.model;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @Author: hujingtian
 * @Date: 2019/7/5 17:34
 * @Description:
 */
@Slf4j
public class AsyncFutureTask extends FutureTask {


    private Thread parentThread;
    private Thread runThread;

    private long startTime = 0;
    private long endTime = 0;


    /**
     * Creates a {@code FutureTask} that will, upon running, execute the
     * given {@code Callable}.
     *
     * @param callable the callable task
     * @throws NullPointerException if the callable is null
     */
    public AsyncFutureTask(Callable callable) {
        super(callable);
        parentThread = Thread.currentThread();
    }

    /**
     * Creates a {@code FutureTask} that will, upon running, execute the
     * given {@code Runnable}, and arrange that {@code get} will return the
     * given result on successful completion.
     *
     * @param runnable the runnable task
     * @param result   the result to return on successful completion. If
     *                 you don't need a particular result, consider using
     *                 constructions of the form:
     *                 {@code Future<?> f = new FutureTask<Void>(runnable, null)}
     * @throws NullPointerException if the runnable is null
     */
    public AsyncFutureTask(Runnable runnable, Object result) {
        super(runnable, result);
    }



    @Override
    public void done() {
       endTime = System.currentTimeMillis();
       log.info("线程: "+Thread.currentThread()+" 执行完毕,共花费时间："+(endTime-startTime)+"ms");
    }


    @Override
    public void run(){
        startTime = System.currentTimeMillis();
        runThread = Thread.currentThread();
        super.run();
    }


    public Thread getParentThread() {
        return parentThread;
    }

    public Thread getRunThread() {
        return runThread;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }
}
