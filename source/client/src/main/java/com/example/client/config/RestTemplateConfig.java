package com.example.client.config;

import com.example.client.client.base.RestCollectionOperationsTemplate;
import com.example.client.handler.RestTemplateResponseErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        var restTemplate = new RestCollectionOperationsTemplate();
        restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());

        return restTemplate;
    }
}
