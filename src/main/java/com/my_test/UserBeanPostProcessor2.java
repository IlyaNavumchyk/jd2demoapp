package com.my_test;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Component
public class UserBeanPostProcessor2 implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {

        System.out.printf("\n2In %s postProcessBeforeInitialization #start\n", beanName);

        Field[] declaredFields = bean.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(PropertyAnnotation.class)) {
                declaredField.setAccessible(true);
                PropertyAnnotation annotation = declaredField.getAnnotation(PropertyAnnotation.class);
                ReflectionUtils.setField(declaredField, bean, annotation.property());
            }
        }

        try {
            Method say = bean.getClass().getMethod("say");
            say.invoke(bean);
        } catch (Exception e) {
            System.out.println("ebat'");
        }

        System.out.printf("2In %s postProcessBeforeInitialization #stop\n", beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        System.out.printf("\n2In %s postProcessAfterInitialization #start\n", beanName);

        Field[] declaredFields = bean.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(PropertyAnnotation.class)) {
                declaredField.setAccessible(true);
                //PropertyAnnotation annotation = declaredField.getAnnotation(PropertyAnnotation.class);
                ReflectionUtils.setField(declaredField, bean, "Common");
            }
        }

        try {
            Method say = bean.getClass().getMethod("say");
            say.invoke(bean);
        } catch (Exception e) {
            System.out.println("ebat'");
        }

        System.out.printf("2In %s postProcessAfterInitialization #stop\n", beanName);

        return bean;
    }
}
