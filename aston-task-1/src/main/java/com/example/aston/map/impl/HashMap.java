package com.example.aston.map.impl;

import com.example.aston.map.Map;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class HashMap<K, V> implements Map<K, V> {
    private Node<K, V>[] data;
    private int size;
    private int capacity = 16;
    private final double LOAD_FACTOR = 0.75;

    private static class Node<K, V> {
        private final K key;
        private V value;
        private final int hash;
        private Node<K, V> next;

        public Node(K key, V value, int hash) {
            this.key = key;
            this.value = value;
            this.hash = hash;
        }
    }

    @SuppressWarnings("unchecked")
    public HashMap() {
        data = new Node[capacity];
    }

    @Override
    public V put(K key, V value) {
        V oldValue = directlyPut(key, value);

        if (oldValue == null && ++size >= LOAD_FACTOR * capacity)
            resize();

        return oldValue;
    }

    private V directlyPut(K key, V value) {
        V oldValue = null;
        int index = index(key);
        Node<K, V> firstNode = data[index];

        if (firstNode == null)
            addFirst(key, value, index);
        else
            oldValue = addLastOrReplaceValue(key, value, firstNode);

        return oldValue;
    }

    private void addFirst(K key, V value, int index) {
        data[index] = newNode(key, value);
    }


    private V addLastOrReplaceValue(K key, V value, Node<K, V> cur) {
        V oldValue = null;

        boolean flag;
        while ((flag = check(key, cur)) && cur.next != null)
            cur = cur.next;

        if (!flag) {
            oldValue = cur.value;
            cur.value = value;

        } else {
            cur.next = newNode(key, value);
        }
        return oldValue;
    }

    @Override
    public V remove(K key) {
        int index = index(key);
        Node<K, V> cur = data[index];
        return removeNode(cur, index, key);
    }

    private V removeNode(Node<K, V> cur, int index, K key) {
        Node<K, V> prev = null;
        V value = null;

        do {
            if (key.hashCode() == cur.hash && key.equals(cur.key)) {
                value = directlyRemove(prev, cur, index);
                break;
            }
            prev = cur;
        } while ((cur = cur.next) != null);

        return value;
    }


    private V directlyRemove(Node<K, V> prev, Node<K, V> cur, int index) {
        if (prev == null)
            data[index] = cur.next;
        else
            prev.next = cur.next;

        size--;
        return cur.value;
    }

    @Override
    public V get(Object key) {
        int index = index(key);
        Node<K, V> cur = data[index];
        V value = null;

        if (cur != null) {
            do {
                if (Objects.hashCode(key) == cur.hash && Objects.equals(key, cur.key)) {
                    value = cur.value;
                    break;
                }
            } while ((cur = cur.next) != null);
        }
        return value;
    }

    private int hash(Object o) {

        return o == null ? 0 : Math.abs(Objects.hashCode(o));
    }


    private int index(Object o) {

        return hash(o) & (capacity - 1);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        for (Node<K, V> datum : data) {
            while (datum != null) {
                set.add(datum.key);
                datum = datum.next;
            }
        }

        return set;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        Node<K, V>[] temp = data;
        data = new Node[capacity *= 2];

        for (Node<K, V> node : temp) {
            while (node != null) {
                rehash(node);
                node = node.next;
            }
        }
    }

    private Node<K, V> newNode(K key, V value) {
        return new Node<>(key, value, Objects.hashCode(key));
    }

    private boolean check(K key, Node<K, V> cur) {

        return Objects.hashCode(key) != cur.hash || !Objects.equals(key, cur.key);
    }

    private void rehash(Node<K, V> node) {
        int index = index(node.key);
        Node<K, V> cur = data[index];

        if (cur == null)
            data[index] = node;
        else {
            boolean flag;
            while ((flag = check(node.key, cur)) && cur.next != null)
                cur = cur.next;
            if (!flag) {
                cur.value = node.value;
            } else {
                cur.next = node;
            }
        }

    }
}
