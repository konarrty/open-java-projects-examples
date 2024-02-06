package com.example.constructionmanagementspring.config;

import com.example.constructionmanagementspring.config.annotation.RequestResponseBodyMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private String pathToResources;
    private final RequestResponseBodyMethod requestResponseBodyMethod;

    public WebMvcConfig(RequestResponseBodyMethod requestResponseBodyMethod) {
        this.requestResponseBodyMethod = requestResponseBodyMethod;
    }

    @Autowired
    public void setPath(@Qualifier("pathToResources") String pathToResources) {
        this.pathToResources = pathToResources;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry
                .addResourceHandler("/image/**")
                .addResourceLocations(pathToResources);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/image/**")
                .allowedOrigins("http://localhost:3000");
    }


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {

        resolvers.add(requestResponseBodyMethod);

    }
}
