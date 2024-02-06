package com.example.tourmanagement.enums;

public enum ReviewType {
    ADVANTAGE("Что понравилось"), DISADVANTAGE("Чего следует ожидать");

    private final String name;

    ReviewType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
