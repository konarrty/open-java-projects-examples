package com.example.aston.list.impl;

import com.example.aston.list.List;

public class ArrayList<T> implements List<T> {
    private Object[] data;
    private int size;
    private final int DEFAULT_CAPACITY = 10;

    public ArrayList() {
        data = new Object[DEFAULT_CAPACITY];
    }

    public ArrayList(int initialCapacity) {
        data = new Object[initialCapacity];
    }

    @SuppressWarnings("unchecked")
    @Override
    public T get(int index) {
        if (isNotValidIndex(index))
            throw new IndexOutOfBoundsException();

        return (T) data[index];
    }

    private void directlyRemove(Object[] temp, int i) {
        int newSize = size - 1;
        if (newSize > i)
            System.arraycopy(temp, i + 1, temp, i, newSize - i);
        temp[size = newSize] = null;
    }

    @Override
    public T add(T element) {
        int curLength = data.length;
        data[size] = element;
        if (curLength == ++size)
            resize();

        return element;
    }

    private boolean isNotValidIndex(int index) {
        return index < 0 || index >= size;
    }


    @SuppressWarnings("unchecked")
    @Override
    public T remove(int index) {
        if (isNotValidIndex(index))
            throw new IndexOutOfBoundsException();

        Object[] temp = data;
        T removedElement = (T) temp[index];
        directlyRemove(temp, index);
        return removedElement;
    }

    @Override
    public int size() {

        return size;
    }

    public int indexOf(Object o) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (o.equals(data[i])) {
                index = i;
                break;
            }
        }

        return index;
    }

    @Override
    public boolean contains(Object o) {

        return indexOf(o) >= 0;
    }

    private void resize() {
        int newLength = data.length + (data.length >> 1) + 1;
        Object[] temp = data;
        data = new Object[newLength];
        System.arraycopy(temp, 0, data, 0, temp.length);
    }
}
