package com.spring.module.async.aop;

import com.spring.module.async.annotation.EnableAsyncExcute;
import com.spring.module.async.jdk.JDKProxy;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;

/**
 * @Author: hujingtian
 * @Date: 2019/7/5 10:49
 * @Description:
 */
// @Component
public class AsyncBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    private JDKProxy jdkProxy;
    /**
     * Apply this BeanPostProcessor to the given new bean instance <i>after</i> any bean
     * initialization callbacks (like InitializingBean's {@code afterPropertiesSet}
     * or a custom init-method). The bean will already be populated with property values.
     * The returned bean instance may be a wrapper around the original.
     * <p>In case of a FactoryBean, this callback will be invoked for both the FactoryBean
     * instance and the objects created by the FactoryBean (as of Spring 2.0). The
     * post-processor can decide whether to apply to either the FactoryBean or created
     * objects or both through corresponding {@code bean instanceof FactoryBean} checks.
     * <p>This callback will also be invoked after a short-circuiting triggered by a
     * {@link InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation} method,
     * in contrast to all other BeanPostProcessor callbacks.
     * <p>The default implementation returns the given {@code bean} as-is.
     *
     * @param bean     the new bean instance
     * @param beanName the name of the bean
     * @return the bean instance to use, either the original or a wrapped one;
     * if {@code null}, no subsequent BeanPostProcessors will be invoked
     * @throws BeansException in case of errors
     * @see InitializingBean#afterPropertiesSet
     * @see FactoryBean
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Annotation[] annotations = AopProxyUtils.ultimateTargetClass(bean).getAnnotations();
        EnableAsyncExcute beanAnnotation = AnnotationUtils.findAnnotation(bean.getClass(),EnableAsyncExcute.class);
        if(beanAnnotation != null){
//            Enhancer enhancer = new Enhancer();
//            AsyncMethodInterceptor asyncMethodInterceptor = new AsyncMethodInterceptor();
//            enhancer.setCallback(asyncMethodInterceptor);
//            enhancer.setSuperclass(bean.getClass());
//            Object ob = enhancer.create();
//            return ob;

            return jdkProxy.newProxy(bean);
        }


        return bean;
    }
}
