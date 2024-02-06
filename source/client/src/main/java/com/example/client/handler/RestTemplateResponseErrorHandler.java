package com.example.client.handler;

import com.example.client.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse httpResponse)
            throws IOException {

        return httpResponse.getStatusCode().is4xxClientError();
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse)
            throws IOException {

        if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new ResourceNotFoundException();
        }
    }
}