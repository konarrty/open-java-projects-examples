package com.example.aston.utils;

import com.example.aston.model.Country;

import java.net.URI;

public class TemperatureUrlUtils {
    public static URI createURI(Country country) {
        String url = "https://api.open-meteo.com/v1/forecast?latitude="
                     + country.getLatitude() + "&longitude="
                     + country.getLongitude()
                     + "&current=temperature_2m,wind_speed_10m";

        return URI.create(url);
    }
}
