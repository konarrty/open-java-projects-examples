package com.example.kpimanagment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.filter.FormContentFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

@Configuration
public class HiddenHttpMethodFilterConfig {

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();

    }

    @Bean
    public FormContentFilter formContentFilter() {
        return new FormContentFilter();
    }

    @Bean
    public ResourceHttpRequestHandler resourceHttpRequestHandler() {
        return new ResourceHttpRequestHandler();
    }


}
