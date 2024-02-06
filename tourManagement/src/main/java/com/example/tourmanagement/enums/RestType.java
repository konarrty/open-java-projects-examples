package com.example.tourmanagement.enums;

import com.example.tourmanagement.dto.EnumTypeDTO;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RestType {

    EXCURSION("Экскурсии"),
    BEACH_REST("Пляжный отдых"),
    CRUISES("Круизы"),
    RECOVERY("Оздоровление"),
    WEDDING_TOURS("Свадебные туры");

    private String name;

    RestType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }

    @JsonValue
    public EnumTypeDTO toDTO() {
        return new EnumTypeDTO(name, ordinal());
    }

    @JsonCreator
    private static RestType byName(RestTypeHelper restType) {

        for (var e : RestType.values()) {
            if (e.getName().equals(restType.getRestType()))
                return e;
        }

        return null;
    }

    @Getter
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class RestTypeHelper {

        private String restType;
    }
}
