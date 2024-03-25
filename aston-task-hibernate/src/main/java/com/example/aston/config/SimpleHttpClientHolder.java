package com.example.aston.config;

import java.net.http.HttpClient;

public class SimpleHttpClientHolder {
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final SimpleHttpClient INSTANCE = new SimpleHttpClient(client);

    private SimpleHttpClientHolder() {
    }

    public static SimpleHttpClient getSimpleHttpClient() {

        return INSTANCE;
    }

}
