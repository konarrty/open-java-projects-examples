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
public enum NutritionType {
    UAI("UAI - Ультра всё включено"),
    AI("AI - Всё включено"),
    FB("FB - Полный пансион"),
    HB("HB - Полупансион"),
    BB("BB - Завтраки"),
    OB("OB - Без питания");

    private String name;

    NutritionType(String name) {
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
    private static NutritionType byName(NutritionTypeHelper typeHelper) {

        for (var e : NutritionType.values()) {
            if (e.getName().equals(typeHelper.getNutritionType()))
                return e;
        }

        return null;
    }

    @Getter
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class NutritionTypeHelper {

        private String nutritionType;
    }


}
