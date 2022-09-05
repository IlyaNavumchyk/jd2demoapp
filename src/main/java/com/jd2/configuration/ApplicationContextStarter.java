package com.jd2.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
//@EnableWebMvc
@ComponentScan("com.jd2")
public class ApplicationContextStarter {
}
