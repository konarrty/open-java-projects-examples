package com.example.tourmanagement.dto.stat;

import com.example.tourmanagement.exception.LogicException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationByAgentAndMonthResultDTO {

    private double[] volume;

    public ReservationByAgentAndMonthResultDTO(int size) {
        this.volume = new double[size];
    }

    public void add(double volume, int position) {

        if (this.volume.length <= position)
            throw new LogicException("Невозможно добавить данные на требуемую позицию");

        this.volume[position] = volume;
    }
}
