package com.spring.module.async.model;


import java.util.concurrent.FutureTask;

/**
 * @Author: hujingtian
 * @Date: 2019/7/5 17:35
 * @Description:
 */
public interface AsyncModel<T>{


    String GET_RESULT = "getResult";

    String GET_STATUS = "getStatus";

    String GET_FUTURE_TASK = "getFutureTask";

    /**
     * 获取返回值结果
     * */
    T getResult();



    /**
     * 是否完成的状态
     * */
    boolean getStatus();


    /**
     * 获取futureTask
     * */
    FutureTask getFutureTask();
}
