package com.example.aston.config;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SimpleObjectMapperHolder {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final SimpleObjectMapper INSTANCE = new SimpleObjectMapper(mapper);

    private SimpleObjectMapperHolder() {
    }

    public static SimpleObjectMapper getSimpleObjectMapper() {

        return INSTANCE;
    }

}
