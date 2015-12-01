package org.v2020.data;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"org.v2020"})
public class DataNeo4jApplication implements CommandLineRunner {

    public DataNeo4jApplication() {
    }

    public void run(String... args) throws Exception {        
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(DataNeo4jApplication.class, args);
    }

}
