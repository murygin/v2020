package org.v2020.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.v2020.data.DataNeo4jConfiguration;

@ComponentScan(basePackages = { "org.v2020" })
@EnableTransactionManagement
@Import(DataNeo4jConfiguration.class)
@SpringBootApplication
public class Application /* extends SpringBootServletInitializer */ {

    
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
