package com.example.aston.list;

public interface List<T> {
    T get(int index);

    T add(T element);

    T remove(int index);

    int size();

    int indexOf(Object o);

    boolean contains(Object o);
}
