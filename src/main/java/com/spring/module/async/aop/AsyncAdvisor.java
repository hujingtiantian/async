package com.spring.module.async.aop;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;

/**
 * @Author: hujingtian
 * @Date: 2019/7/5 10:53
 * @Description:
 */
public class AsyncAdvisor implements Advisor {
    @Override
    public Advice getAdvice() {
        return null;
    }

    @Override
    public boolean isPerInstance() {
        return false;
    }
}
