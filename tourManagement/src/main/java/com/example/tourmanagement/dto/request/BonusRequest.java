package com.example.tourmanagement.dto.request;

import com.example.tourmanagement.dto.GridAwardDTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BonusRequest {

    private Long id;

    @Min(2020)
    private int year;

    @Max(12)
    @Min(1)
    private int month;

    private Long agentId;

    private GridAwardDTO gridAward;

}
