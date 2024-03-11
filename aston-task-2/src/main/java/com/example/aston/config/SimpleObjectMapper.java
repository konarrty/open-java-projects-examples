package com.example.aston.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SimpleObjectMapper {
    private final ObjectMapper mapper;

    SimpleObjectMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public <T> T readAndConvert(String body, Class<T> clazz) {
        try {
            return mapper.readValue(body, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
