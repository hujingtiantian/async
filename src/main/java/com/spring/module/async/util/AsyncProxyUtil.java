package com.spring.module.async.util;

import cn.hutool.core.util.ArrayUtil;
import com.spring.module.async.model.AsyncModel;
import com.spring.module.async.proxy.AsyncCallFilter;
import com.spring.module.async.proxy.AsyncFutureTaskMethodInterceptor;
import com.spring.module.async.proxy.AsyncResultMethodInterceptor;
import com.spring.module.async.proxy.AsyncStatusMethodInterceptor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.FutureTask;

/**
 * @Author: hujingtian
 * @Date: 2019/7/8 13:26
 * @Description:
 */
public class AsyncProxyUtil {
    public static Object cglibNewProxy(Class retClass, FutureTask futureTask) {
        Enhancer enhancer = new Enhancer();
        if(retClass.isInterface()){
            enhancer.setInterfaces(new Class[]{retClass.getClass(),AsyncModel.class});
        }else {
            enhancer.setSuperclass(retClass);
            enhancer.setInterfaces(new Class[]{AsyncModel.class});
        }
        enhancer.setCallbackFilter(new AsyncCallFilter());
        enhancer.setCallbacks(new MethodInterceptor[]{new AsyncResultMethodInterceptor(futureTask),new AsyncStatusMethodInterceptor(futureTask),new AsyncFutureTaskMethodInterceptor(futureTask)});
        return enhancer.create();
    }


    public static Object jdkNewProxy(Class retClass, FutureTask futureTask){
        if(retClass.isInterface()){
            InvocationHandler invocationHandler = new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    switch (method.getName()){
                        case AsyncModel.GET_RESULT : return futureTask.get();
                        case AsyncModel.GET_STATUS : return futureTask.isDone();
                        case AsyncModel.GET_FUTURE_TASK : return futureTask;
                    }
                    return method.invoke(proxy,args);
                }
            };
            Class[] clazz = ArrayUtil.append(retClass.getClass().getInterfaces(),AsyncModel.class,retClass);
            return Proxy.newProxyInstance(AsyncModel.class.getClassLoader(),clazz,invocationHandler);
        }
        return cglibNewProxy(retClass,futureTask);
    }
}
