package com.spring.module.async.test;


import com.spring.module.async.threadpool.AsyncThreadPool;
import com.spring.module.async.util.AsyncProxyUtil;
import com.spring.module.async.util.AsyncResultUtil;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @Author: hujingtian
 * @Date: 2019/7/5 16:51
 * @Description:
 */
public class Test {

    public static void main(String[] args){
//        long l1 = System.currentTimeMillis();
//        TestServiceImpl testService = new TestServiceImpl();
//        testService.msg1("1");
//        testService.msg2("1");
//        testService.msg3("1");
//        System.out.println(System.currentTimeMillis()-l1);

        long l1 = System.currentTimeMillis();
        Enhancer enhancer = new Enhancer();
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                FutureTask futureTask = new FutureTask(new Callable() {
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
        System.out.println(System.currentTimeMillis()-l1);
        Object o1 = testService.msg1("1");
        Object o2 = testService.msg2("1");
        Object o3 = testService.msg3("1");
        System.out.println(System.currentTimeMillis()-l1);
        AsyncResultUtil.getResult(o1);
        AsyncResultUtil.getResult(o2);
        AsyncResultUtil.getResult(o3);
        System.out.println(System.currentTimeMillis()-l1);
    }




}
