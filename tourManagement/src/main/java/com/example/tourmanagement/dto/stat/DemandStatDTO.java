package com.example.tourmanagement.dto.stat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DemandStatDTO {

    private List<DataSetDTO> dataSets;
}
