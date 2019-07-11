package com.spring.module.async.init;

import com.spring.module.async.annotation.EnableAsyncExcute;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: hujingtian
 * @Date: 2019/7/4 14:31
 * @Description: 存储异步类的工厂
 */
@Component
public class AsyncApplicationContext implements ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, Object> asyncLoadBeanMap = applicationContext
                .getBeansWithAnnotation(EnableAsyncExcute.class);

        for (Object beanObj : asyncLoadBeanMap.values()) {
            // 因为被拦截器拦住，使用cglib进行代理，所有需要获得对应的原始类
            Class<?> originBeanClass = AopProxyUtils.ultimateTargetClass(beanObj);


//
//            AsyncLoadExecutor asyncLoadExecutor = parseClassAnnotation(originBeanClass);
//
//            parseMethodAnnotation(originBeanClass, asyncLoadExecutor);
        }
    }
}
