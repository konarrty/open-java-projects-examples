package com.example.aston.map;

import java.util.Set;

public interface Map<K, V> {

    V put(K key, V value);

    V remove(K key);

    V get(Object key);

    int size();

    Set<K> keySet();
}
