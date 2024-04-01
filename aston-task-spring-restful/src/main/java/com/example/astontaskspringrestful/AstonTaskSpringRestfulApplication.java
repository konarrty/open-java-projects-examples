package com.example.astontaskspringrestful;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class AstonTaskSpringRestfulApplication {

    public static void main(String[] args) {
        SpringApplication.run(AstonTaskSpringRestfulApplication.class, args);
    }

}
