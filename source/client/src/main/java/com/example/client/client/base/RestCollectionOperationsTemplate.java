package com.example.client.client.base;

import com.example.client.exception.ResourceNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

@Log4j2
@Component
public class RestCollectionOperationsTemplate extends RestTemplate implements RestCollectionOperations {

    @Override
    public <T> Collection<T> getCollectionForElementClass(String url, Class<T> clazz) {

        Type type = ResolvableType.forClassWithGenerics(ArrayList.class, clazz).getType();
        ParameterizedTypeReference<Collection<T>> reference = ParameterizedTypeReference.forType(type);

        return exchange(
                url,
                HttpMethod.GET,
                null,
                reference)
                .getBody();

    }

    @Override
    public <T> Collection<T> getCollectionForElementClass(String url, Class<T> clazz, String errorMessage) {
        try {
            return getCollectionForElementClass(url, clazz);
        } catch (ResourceNotFoundException ex) {
            log.error(errorMessage);
            return null;
        }
    }

}
