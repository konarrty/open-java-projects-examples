
package com.example.tourmanagement.config;

import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.util.Objects;

public class RestTemplateHeaderModifierInterceptor
        implements ClientHttpRequestInterceptor {

    @NonNull
    @Override
    public ClientHttpResponse intercept(
            @NonNull HttpRequest request,
            @NonNull byte[] body,
            ClientHttpRequestExecution execution) throws IOException {

        ClientHttpResponse response = execution.execute(request, body);
        if (Objects.requireNonNull(response.getHeaders()
                        .getFirst("Content-Type"))
                .split(";")[0]
                .equals("draft-sdmx-json"))
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        return response;
    }

}