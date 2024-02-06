package com.example.constructionmanagementspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ConstructionManagementSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConstructionManagementSpringApplication.class, args);
    }

}
