package com.example.tourmanagement.dto.stat;

import com.example.tourmanagement.model.entity.Agent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationByAgentDTO {

    private Agent agent;

    private double volume;
}
