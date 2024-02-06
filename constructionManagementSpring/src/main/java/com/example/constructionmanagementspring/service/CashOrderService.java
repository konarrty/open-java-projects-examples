package com.example.constructionmanagementspring.service;

import com.example.constructionmanagementspring.dto.statDto.InventoryPerMonthStatDto;
import com.example.constructionmanagementspring.model.CashOrder;

import java.time.LocalDateTime;
import java.util.List;

public interface CashOrderService {
    List<CashOrder> getAllCashOrders(int page);

    CashOrder getCashOrderById(Long id);

    CashOrder createCashOrder(CashOrder cashOrders);

    CashOrder putCashOrder(CashOrder cashOrders, Long cashOrdersId);

    void deleteCashOrder(Long id);

    String conductInventory(LocalDateTime after, LocalDateTime before);

    List<InventoryPerMonthStatDto> conductInventoryPerMonth();

    double calculateIncome();

    double calculateIncomeForPeriod(LocalDateTime after, LocalDateTime before);

    double getTrafficPerLastMonth(Long beforeLag, Long afterLag);

    double getPercentDifferenceBetweenLastAndCurrentMonth();


}
