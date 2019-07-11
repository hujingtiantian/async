package com.spring.module.async.util;

import com.spring.module.async.model.AsyncModel;

import java.util.concurrent.FutureTask;

/**
 * @Author: hujingtian
 * @Date: 2019/7/8 14:11
 * @Description:
 */
public class AsyncResultUtil<T> {
    public static <T>T getResult(T object) {
        if(!checkType(object)){
            return object;
        }
        T t = ((AsyncModel<T>)object).getResult();
        return t;
    }


    public static  boolean getStatus(Object object) {
        if(!checkType(object)){
            return true;
        }
        boolean flag = ((AsyncModel)object).getStatus();
        return flag;
    }


    public static FutureTask getFutureTask(Object object) {
        if(!checkType(object)){
            return null;
        }
        FutureTask futureTask = ((AsyncModel)object).getFutureTask();
        return futureTask;
    }



    /**
     *  检验是否是AsyncModel类型的参数
     *  如果不是 代表该返回值类型不支持动态代理，该方法为同步进行的
     * @param o
     * @return
     * */
    private static boolean checkType(Object o){
        if(o instanceof AsyncModel){
            return true;
        }else {
            return false;
        }
    }
}
