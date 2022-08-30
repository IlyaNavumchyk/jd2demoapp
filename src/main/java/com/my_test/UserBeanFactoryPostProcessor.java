package com.my_test;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Iterator;

@Component
public class UserBeanFactoryPostProcessor implements BeanFactoryPostProcessor {


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {

        System.out.println("\nIn postProcessBeanFactory #start");

        //Можно посмотреть все BeanDefinitions
        /*System.out.println("Show all bean definition");
        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }*/

        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("user");

        Method[] declaredMethods = ReflectionUtils.getDeclaredMethods(User.class);
        for (Method declaredMethod : declaredMethods) {
            try {
                if (declaredMethod.getName().equals("say")) {
                    System.out.println("find method say");
                    declaredMethod.invoke(beanDefinition);
                }
            } catch (Exception e) {
                System.out.println("Exception in UserBFPP");
            }
        }

        System.out.println("In postProcessBeanFactory #stop");
    }
}
