package dto;

import java.io.Serializable;

public record MonthDTO(String name, Integer days, Integer number) implements Serializable {

    @Override
    public String toString() {

        return name;
    }
}