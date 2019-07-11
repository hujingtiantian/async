package com.spring.module.async.proxy;

import com.spring.module.async.model.AsyncModel;
import org.springframework.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;

/**
 * @Author: hujingtian
 * @Date: 2019/7/11 11:19
 * @Description:
 */
public class AsyncCallFilter implements CallbackFilter {


    @Override
    public int accept(Method method) {
        if(AsyncModel.GET_RESULT.equals(method.getName())){
            return 0;
        }else if(AsyncModel.GET_STATUS.equals(method.getName())){
            return 1;
        }else if(AsyncModel.GET_FUTURE_TASK.equals(method.getName())){
            return 2;
        }
        return 0;
    }
}
