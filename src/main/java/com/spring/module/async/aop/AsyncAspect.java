package com.spring.module.async.aop;

import com.spring.module.async.annotation.EnableAsyncExcute;
import com.spring.module.async.model.AsyncFutureTask;
import com.spring.module.async.threadpool.AsyncThreadPool;
import com.spring.module.async.util.AsyncProxyUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @Author: hujingtian
 * @Date: 2019/7/8 12:24
 * @Description:
 */
@Aspect
@Component
public class AsyncAspect {

    @Around(value = "@within(enableAsyncExcute)",argNames = "point,enableAsyncExcute")
    public Object aroundClass(ProceedingJoinPoint point, EnableAsyncExcute enableAsyncExcute) throws Throwable {
        Signature signature = point.getSignature();
        Class retClass = ((MethodSignature)signature).getReturnType();
        if(!checkRetClass(retClass)){
            return point.proceed();
        }
        AsyncFutureTask futureTask = new AsyncFutureTask(new Callable() {
            @Override
            public Object call() throws Exception {
                try {
                    return  point.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
                return null;
            }
        });
        AsyncThreadPool.submit(futureTask);
        return AsyncProxyUtil.jdkNewProxy(retClass,futureTask);
    }

    @Around(value = "@annotation(enableAsyncExcute)",argNames = "point,enableAsyncExcute")
    public Object aroundMethod(ProceedingJoinPoint point,EnableAsyncExcute enableAsyncExcute) throws Throwable {
        return aroundClass(point,enableAsyncExcute);
    }


    private boolean checkRetClass(Class clazz){
        // 基本类型无法生成代理类
        if(clazz.isPrimitive()){
            return false;
        }
        return true;
    }
}
