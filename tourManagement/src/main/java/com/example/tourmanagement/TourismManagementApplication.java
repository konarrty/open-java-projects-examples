package com.example.tourmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TourismManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(TourismManagementApplication.class, args);
    }

}
