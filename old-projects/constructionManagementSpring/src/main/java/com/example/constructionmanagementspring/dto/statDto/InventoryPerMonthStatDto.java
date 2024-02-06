package com.example.constructionmanagementspring.dto.statDto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class InventoryPerMonthStatDto {

    private LocalDateTime dateTime;

    private Double sum;
}
