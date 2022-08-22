package com.jd2.listener;

import com.jd2.filter.CharsetFilter;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartupApplicationListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Context is UP.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Context is DOWN.");
    }
}
