package com.example.aston.api;

import com.example.aston.config.SimpleHttpClient;
import com.example.aston.config.SimpleHttpClientHolder;
import com.example.aston.config.SimpleObjectMapper;
import com.example.aston.config.SimpleObjectMapperHolder;

public class TemperatureApiHolder {
    private static final SimpleHttpClient client = SimpleHttpClientHolder.getSimpleHttpClient();
    private static final SimpleObjectMapper mapper = SimpleObjectMapperHolder.getSimpleObjectMapper();
    private static final TemperatureApi INSTANCE = new TemperatureApi(client, mapper);

    private TemperatureApiHolder() {
    }

    public static TemperatureApi getInstance() {

        return INSTANCE;
    }

}
