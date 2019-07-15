package com.spring.threadlocal;

import com.spring.module.async.annotation.EnableAsyncExcute;

/**
 * @Author: hujingtian
 * @Date: 2019/7/5 16:52
 * @Description:
 */
@EnableAsyncExcute
public class TestServiceImpl {

    public Object msg1(String msg,ThreadLocal threadLocal){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("msg1:"+threadLocal.get());
        return msg;
    }


    public Object msg2(String msg,ThreadLocal threadLocal){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("msg2:"+threadLocal.get());
        return msg;
    }

    public Object msg3(String msg,ThreadLocal threadLocal){
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.out.println("msg3:"+threadLocal.get());
        return msg;
    }

}
