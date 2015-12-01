package org.v2020.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"org.v2020.service"})
public class ServiceApplication implements CommandLineRunner {

    public ServiceApplication() {
    }
    
    public void run(String... args) throws Exception {        
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ServiceApplication.class, args);
    }

}
