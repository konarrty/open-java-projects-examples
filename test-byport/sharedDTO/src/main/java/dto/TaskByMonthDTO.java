package dto;

import java.io.Serializable;

public record TaskByMonthDTO(Long id, String name, RangeDTO range) implements Serializable {

    @Override
    public String toString() {
        return "Работа " + name + " с периодом выполнения " + range;
    }
}