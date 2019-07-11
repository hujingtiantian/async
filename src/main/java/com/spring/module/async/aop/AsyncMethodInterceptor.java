package com.spring.module.async.aop;


import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @Author: hujingtian
 * @Date: 2019/7/5 09:58
 * @Description:
 */
public class AsyncMethodInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        final Object finObj = o;
        final Object[] finArgs = objects;
        final Method finMethod = method;

        Class returnClass = method.getReturnType();
        if (Void.TYPE.isAssignableFrom(returnClass)) {
            // 不处理void的函数调用
            return finMethod.invoke(finObj, finArgs);
        } else if (!Modifier.isPublic(returnClass.getModifiers())) {
            // 处理如果是非public属性，则不进行代理，强制访问会出现IllegalAccessException，比如一些内部类或者匿名类不允许直接访问
            return finMethod.invoke(finObj, finArgs);
        } else if (Modifier.isFinal(returnClass.getModifiers())) {
            // 处理特殊的final类型，目前暂不支持，后续可采用jdk proxy
            return finMethod.invoke(finObj, finArgs);
        } else if (returnClass.isPrimitive() || returnClass.isArray()) {
            // 不处理特殊类型，因为无法使用cglib代理
            return finMethod.invoke(finObj, finArgs);
        } else if (returnClass == Object.class) {
            // 针对返回对象是Object类型，不做代理。没有具体的method，代理没任何意义
            return finMethod.invoke(finObj, finArgs);
        } else {

            FutureTask futureTask = new FutureTask(new Callable() {
                @Override
                public Object call() throws Exception {
                    try {
                        return methodProxy.invokeSuper(o,objects);
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                    return null;
                }
            });
            new Thread(futureTask).start();
//            // 够造一个返回的AsyncLoadResult
//            AsyncLoadResult result = new AsyncLoadResult(returnClass, future, timeout);
//            // 继续返回一个代理对象
//            AsyncLoadObject asyncProxy = (AsyncLoadObject) result.getProxy();
//            // 添加到barrier中
//            if (config.getNeedBarrierSupport()) {
//                AsyncLoadBarrier.addTask((AsyncLoadObject) asyncProxy);
//            }
//            // 返回对象
            return futureTask.get();
        }
    }
}
