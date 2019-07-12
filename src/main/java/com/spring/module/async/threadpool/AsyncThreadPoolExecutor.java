package com.spring.module.async.threadpool;

import com.spring.module.async.model.AsyncFutureTask;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.concurrent.*;

/**
 * @Author: hujingtian
 * @Date: 2019/7/5 17:32
 * @Description: 异步处理线程池
 */
public class AsyncThreadPoolExecutor extends ThreadPoolExecutor {


    private static final Field threadLocals = ReflectionUtils.findField(Thread.class,"threadLocals");
    private static final Field inheritableThreadLocals = ReflectionUtils.findField(Thread.class,"inheritableThreadLocals");


    static {
        ReflectionUtils.makeAccessible(threadLocals);
        ReflectionUtils.makeAccessible(inheritableThreadLocals);
    }



    public AsyncThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public AsyncThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public AsyncThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public AsyncThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    public Future submit(Runnable runnable){
       return super.submit(runnable);
    }


    public Future submit(AsyncFutureTask asyncFutureTask){

        //这个地方的线程仍旧为主线程
        //重写submit方法来获取AsyncFutureTask 这里不再封装Future 因为传入的本身就是一个futureTask

        if (asyncFutureTask == null) throw new NullPointerException();
        execute(asyncFutureTask);
        return asyncFutureTask;
    }


    @Override
    protected void beforeExecute(Thread thread,Runnable command){
        if(command instanceof AsyncFutureTask){
            if(ReflectionUtils.getField(threadLocals,((AsyncFutureTask) command).getParentThread()) != null){
               Object o = ReflectionUtils.getField(threadLocals,Thread.currentThread());
                ReflectionUtils.setField(threadLocals,Thread.currentThread(),ReflectionUtils.getField(threadLocals,((AsyncFutureTask) command).getParentThread()));
                ReflectionUtils.setField(inheritableThreadLocals,Thread.currentThread(),ReflectionUtils.getField(inheritableThreadLocals,((AsyncFutureTask) command).getParentThread()));
            }
        }
    }



    @Override
    protected void afterExecute(Runnable r, Throwable t) {


        ReflectionUtils.setField(threadLocals,Thread.currentThread(),null);

        ReflectionUtils.setField(inheritableThreadLocals,Thread.currentThread(),null);

    }
}
