package com.spring.module.async.proxy;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.concurrent.FutureTask;

/**
 * @Author: hujingtian
 * @Date: 2019/7/11 11:25
 * @Description:
 */
public class AsyncStatusMethodInterceptor implements MethodInterceptor {

    private FutureTask futureTask;

    public AsyncStatusMethodInterceptor(FutureTask futureTask){
        this.futureTask = futureTask;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        return futureTask.isDone();
    }
}
