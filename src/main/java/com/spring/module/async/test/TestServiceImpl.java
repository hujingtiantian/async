package com.spring.module.async.test;

import com.spring.module.async.annotation.EnableAsyncExcute;

/**
 * @Author: hujingtian
 * @Date: 2019/7/5 16:52
 * @Description:
 */
@EnableAsyncExcute
public class TestServiceImpl {

    public Object msg1(String msg){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("1"+msg);
        return msg;
    }


    public Object msg2(String msg){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("2"+msg);
        return msg;
    }

    public Object msg3(String msg){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("3"+msg);
        return msg;
    }
}
