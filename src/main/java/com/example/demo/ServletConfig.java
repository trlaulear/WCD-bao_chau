package com.example.demo;

import com.example.demo.servlet.FormServlet;
import com.example.demo.servlet.HelloServlet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;

@Configuration   // 🔥 THIS IS REQUIRED

public class ServletConfig {

    @Bean
    public ServletRegistrationBean<FormServlet> formServlet() {

        System.out.println("Registering /my-form");

        FormServlet servlet = new FormServlet();

        ServletRegistrationBean<FormServlet> bean =
                new ServletRegistrationBean<>(servlet);

        bean.addUrlMappings("/legacy/*");

        return bean;
    }

    @Bean
    public ServletRegistrationBean<HelloServlet> helloServlet() {

        HelloServlet servlet = new HelloServlet();

        ServletRegistrationBean<HelloServlet> bean =
                new ServletRegistrationBean<>(servlet);

        bean.addUrlMappings("/hello");

        return bean;
    }
}