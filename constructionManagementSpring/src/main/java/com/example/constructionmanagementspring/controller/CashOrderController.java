package com.example.constructionmanagementspring.controller;

import com.example.constructionmanagementspring.config.annotation.BodyToEntity;
import com.example.constructionmanagementspring.dto.CashOrderDto;
import com.example.constructionmanagementspring.dto.statDto.InventoryPerMonthStatDto;
import com.example.constructionmanagementspring.model.CashOrder;
import com.example.constructionmanagementspring.service.CashOrderService;
import com.example.constructionmanagementspring.utils.ConstantUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/orders")
@CrossOrigin({"http://localhost:3000", "http://localhost:3001"})
@RequiredArgsConstructor
public class CashOrderController {

    private final CashOrderService cashOrdersService;


    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @GetMapping({"/", ""})
    public ResponseEntity<?> getAllCashOrders(@RequestParam int page) {
        List<CashOrder> cashOrdersList = cashOrdersService.getAllCashOrders(page);
        if (!cashOrdersList.isEmpty())
            return ResponseEntity.ok(cashOrdersList);
        else
            return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping({"/", ""})
    public CashOrder createCashOrders(@Valid @BodyToEntity(CashOrderDto.class) CashOrder cashOrder) {

        return cashOrdersService.createCashOrder(cashOrder);
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public CashOrder putCashOrders(@Valid @BodyToEntity(CashOrderDto.class) CashOrder cashOrder, @PathVariable Long id) {

        return cashOrdersService.putCashOrder(cashOrder, id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteCashOrders(@PathVariable Long id) {

        cashOrdersService.deleteCashOrder(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public CashOrder getCashOrdersById(@PathVariable Long id) {

        return cashOrdersService.getCashOrderById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/profit")
    public double calculateIncome() {

        return cashOrdersService.calculateIncome();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/profitForPeriod")
    public double calculateIncomeForPeriod(@RequestParam("afterDateTime") LocalDateTime afterDateTime,
                                           @RequestParam("beforeDateTime") LocalDateTime beforeDateTime) {

        return cashOrdersService
                .calculateIncomeForPeriod(afterDateTime, beforeDateTime);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/inventory")
    public String conductInventory(@RequestParam(value = "afterDateTime", required = false) LocalDateTime afterDateTime,
                                   @RequestParam(value = "beforeDateTime", required = false) LocalDateTime beforeDateTime) {
        System.err.println(afterDateTime + " " + beforeDateTime);
        return cashOrdersService
                .conductInventory(afterDateTime, beforeDateTime);

    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/statistic")
    public List<InventoryPerMonthStatDto> conductInventoryPerMonth() {

        return cashOrdersService.conductInventoryPerMonth();
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/traffic")
    public double getTrafficForLastMonth(@RequestParam(value = "beforeMonthLag", required = false, defaultValue = "0") Long beforeMonthLag
            , @RequestParam(value = "afterMonthLag", required = false, defaultValue = "1") Long afterMonthLag) {

        return cashOrdersService.getTrafficPerLastMonth(beforeMonthLag, afterMonthLag);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/traffic-percent")
    public double getPercentDifferenceBetweenLastAndCurrentMonth() {

        return cashOrdersService.getPercentDifferenceBetweenLastAndCurrentMonth();
    }

}
