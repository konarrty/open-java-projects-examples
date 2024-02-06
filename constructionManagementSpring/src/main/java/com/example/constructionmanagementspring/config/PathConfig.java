package com.example.constructionmanagementspring.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.io.IOException;

@Configuration
public class PathConfig {

    @Qualifier("pathToResources")
    @Bean
    public String pathToResources() throws IOException {

        return fileSystemXmlApplicationContext()
                .getResource("src/main/resources/")
                .getURL()
                .toExternalForm();
    }

    @Bean
    public FileSystemXmlApplicationContext fileSystemXmlApplicationContext() {

        return new FileSystemXmlApplicationContext();
    }

}
