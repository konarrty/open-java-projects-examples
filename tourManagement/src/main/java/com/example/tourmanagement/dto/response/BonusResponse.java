package com.example.tourmanagement.dto.response;

import com.example.tourmanagement.dto.AgentDTO;
import com.example.tourmanagement.dto.GridAwardDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BonusResponse {

    private Long id;

    private int year;

    private int month;

    private AgentDTO agent;

    private GridAwardDTO gridAward;

    private double sum;

}
