package com.spring.threadlocal;


import com.spring.module.async.model.AsyncFutureTask;
import com.spring.module.async.threadpool.AsyncThreadPool;
import com.spring.module.async.util.AsyncProxyUtil;
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
//        long l1 = System.currentTimeMillis();
//        TestServiceImpl testService = new TestServiceImpl();
//        testService.msg1("1");
//        testService.msg2("1");
//        testService.msg3("1");
//        System.out.println(System.currentTimeMillis()-l1);
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
//        Object o1 = testService.msg1("1",threadLocal);
//        Object o2 = testService.msg2("1",threadLocal);
//        Object o3 = testService.msg3("1",threadLocal);
        Object o31 = testService.msg2("1",threadLocal);
        Object o32 = testService.msg2("1",threadLocal);
        Object o33 = testService.msg2("1",threadLocal);
        Object o34 = testService.msg2("1",threadLocal);
        Object o35 = testService.msg2("1",threadLocal);
        Object o36 = testService.msg2("1",threadLocal);
        Object o37 = testService.msg2("1",threadLocal);
        Object o38 = testService.msg2("1",threadLocal);
        Object o39 = testService.msg2("1",threadLocal);

        Object o361 = testService.msg2("1",threadLocal);
        Object o372 = testService.msg2("1",threadLocal);
        Object o383 = testService.msg2("1",threadLocal);
        Object o394 = testService.msg2("1",threadLocal);


        Object o3611 = testService.msg2("1",threadLocal);
        Object o3722 = testService.msg2("1",threadLocal);
        Object o3833 = testService.msg2("1",threadLocal);
        Object o3945 = testService.msg2("1",threadLocal);



        ThreadLocal threadLocal1 = new ThreadLocal();
//        threadLocal1.set("testo4");
//        Object o4 = testService.msg3("1",threadLocal1);
    }




}
