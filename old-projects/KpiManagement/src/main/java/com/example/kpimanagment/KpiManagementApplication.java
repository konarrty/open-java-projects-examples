package com.example.kpimanagment;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@EnableAsync
@RequiredArgsConstructor
public class KpiManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(KpiManagementApplication.class, args);

    }

}
