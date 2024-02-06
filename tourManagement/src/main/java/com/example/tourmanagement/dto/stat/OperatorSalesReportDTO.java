package com.example.tourmanagement.dto.stat;

import com.example.tourmanagement.enums.RestType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OperatorSalesReportDTO {

    private Double volume;

    private RestType name;

}
