package org.v2020.service;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;

@Configuration
@EnableNeo4jRepositories(basePackages = "org.v2020.data")
@ComponentScan(basePackages = {"org.v2020.data.dao.iso","org.v2020.service"})
public class Application extends Neo4jConfiguration implements CommandLineRunner {

    public Application() {
        setBasePackage("org.v2020.service","org.v2020.data");
    }
    
    @Bean
    GraphDatabaseService graphDatabaseService() {
        return new GraphDatabaseFactory().newEmbeddedDatabase("v2020-neo4j.db");
    }

    public void run(String... args) throws Exception {        
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

}
