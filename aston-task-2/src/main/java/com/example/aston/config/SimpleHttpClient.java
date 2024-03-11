package com.example.aston.config;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SimpleHttpClient {

    private final HttpClient client;

    SimpleHttpClient(HttpClient client) {
        this.client = client;
    }

    public HttpResponse<String> sendAndReceive(HttpRequest request) {
        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
