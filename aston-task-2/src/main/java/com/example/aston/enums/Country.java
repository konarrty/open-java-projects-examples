package com.example.aston.enums;

import lombok.Getter;

@Getter
public enum Country {
    MOSCOW("Москва", 55.7472, 37.618),
    SAINT_PETERSBURG("Санкт-Петербург", 59.57, 30.19),
    KAZAN("Казань", 55.47, 49.07),
    YEKATERINBURG("Екатеринбург", 56.85, 60.61),
    SAMARA("Самара", 53.12, 50.06),
    NIZHNY_NOVGOROD("Нижний Новгород", 56.18, 43.52),
    BREST("Брест", 52.09, 23.68),
    MINSK("Минск", 53.55, 27.33);

    private final String name;
    private final double latitude;
    private final double longitude;

    Country(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
