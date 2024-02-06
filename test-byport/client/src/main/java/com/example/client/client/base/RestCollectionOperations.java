package com.example.client.client.base;

import java.util.Collection;

public interface RestCollectionOperations {
    <T> Collection<T> getCollectionForElementClass(String url, Class<T> clazz);

    <T> Collection<T> getCollectionForElementClass(String url, Class<T> clazz, String errorMessage);
}
