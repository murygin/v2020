package org.v2020.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"org.v2020"})
@EnableAutoConfiguration
public class Application /*extends SpringBootServletInitializer*/ {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    /*
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }

    private static Class<Application> applicationClass = Application.class;
    */
}
