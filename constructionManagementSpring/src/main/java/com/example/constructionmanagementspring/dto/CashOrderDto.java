package com.example.constructionmanagementspring.dto;

import com.example.constructionmanagementspring.enums.OrderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CashOrderDto {

    private Long cashOrderId;

    private double price;

    private OrderType type;

    private LocalDateTime dateTime;

    private String contract;

    private String ttnNumber;

    private AssetsDto assets;


}
