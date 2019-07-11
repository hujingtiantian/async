package com.spring.module.async.jdk;

import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @Author: hujingtian
 * @Date: 2019/7/8 10:59
 * @Description:
 */
@Component
public class JDKProxy implements InvocationHandler {


    public Object newProxy(Object proxy){
        return Proxy.newProxyInstance(proxy.getClass().getClassLoader(),proxy.getClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        FutureTask futureTask = new FutureTask(new Callable() {
            @Override
            public Object call() throws Exception {
                return method.invoke(proxy,args);
            }
        });
        new Thread(futureTask).start();
        return futureTask.get();
    }


}
