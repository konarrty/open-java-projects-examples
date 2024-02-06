package com.example.tourmanagement.enums;

import com.example.tourmanagement.dto.EnumTypeDTO;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public enum ReservationStatus {
    PROCESSING("Обрабатывается"),
    APPROVED("Одобрена"),
    PAID("Оплачена"),
    CONFIRMED("Подтверждена"),
    REJECTED("Отменена");

    private String name;

    ReservationStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public EnumTypeDTO toDTO() {
        return new EnumTypeDTO(name, ordinal());
    }

}
