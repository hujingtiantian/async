package com.spring.threadlocal;


import com.spring.module.async.model.AsyncFutureTask;
import com.spring.module.async.threadpool.AsyncThreadPool;
import com.spring.module.async.util.AsyncProxyUtil;
import com.spring.module.async.util.AsyncResultUtil;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * @Author: hujingtian
 * @Date: 2019/7/5 16:51
 * @Description:
 */
public class TestThreadLocal {

    public static void main(String[] args){
        ThreadLocal threadLocal = new ThreadLocal();
        threadLocal.set("123");
        long l1 = System.currentTimeMillis();
        Enhancer enhancer = new Enhancer();
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                AsyncFutureTask futureTask = new AsyncFutureTask(new Callable() {
                    @Override
                    public Object call() throws Exception {
                        try {
                            return  methodProxy.invokeSuper(o,objects);
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                        return null;
                    }
                });
                AsyncThreadPool.submit(futureTask);
                return AsyncProxyUtil.jdkNewProxy(method.getReturnType(),futureTask);
            }
        });
        enhancer.setSuperclass(TestServiceImpl.class);
        TestServiceImpl testService = (TestServiceImpl) enhancer.create();
        Object o1 = testService.msg1("1",threadLocal);
        AsyncResultUtil.getResult(o1);
    }




}
