package com.example.constructionmanagementspring.service.impl;

import com.example.constructionmanagementspring.dto.statDto.InventoryPerMonthStatDto;
import com.example.constructionmanagementspring.enums.EquipmentStatus;
import com.example.constructionmanagementspring.enums.OrderType;
import com.example.constructionmanagementspring.exception.EntityAlreadyExistException;
import com.example.constructionmanagementspring.exception.NoSuchEntityException;
import com.example.constructionmanagementspring.model.Assets;
import com.example.constructionmanagementspring.model.CashOrder;
import com.example.constructionmanagementspring.repository.CashOrderRepository;
import com.example.constructionmanagementspring.service.AssetsService;
import com.example.constructionmanagementspring.service.CashOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CashOrderServiceImpl implements CashOrderService {

    private final CashOrderRepository cashOrderRepository;

    private final AssetsService assetsService;

    @Override
    public List<CashOrder> getAllCashOrders(int page) {

        return cashOrderRepository.findAll(
                        PageRequest.of(page - 1, 5))
                .getContent();
    }

    @Override
    public CashOrder createCashOrder(CashOrder cashOrder) {

        if (cashOrderRepository.findAll().stream().anyMatch(order
                -> order.getAssets().getId().equals(cashOrder.getAssets().getId())))
            throw new EntityAlreadyExistException("Актив уже оформлен!");

        return cashOrderRepository.save(cashOrder);
    }

    @Override
    public CashOrder putCashOrder(CashOrder newCashOrder, Long cashOrderId) {

        CashOrder cashOrder = cashOrderRepository
                .findById(cashOrderId)
                .orElseThrow(() ->
                        new NoSuchEntityException("Кассовый ордер не найден!"));

        newCashOrder.setId(cashOrder.getId());

        return cashOrderRepository.save(
                joinEntities(newCashOrder));
    }

    @Override
    public void deleteCashOrder(Long id) {
        if (!cashOrderRepository.existsById(id))
            throw new NoSuchEntityException("Кассовый ордер не найден!");

        cashOrderRepository.deleteById(id);
    }

    @Override
    public CashOrder getCashOrderById(Long id) {

        return cashOrderRepository
                .findById(id)
                .orElseThrow(() ->
                        new NoSuchEntityException("Кассовый ордер не найден!"));
    }

    @Override
    public double calculateIncome() {
        double plus = 0.0;
        double minus = 0.0;

        List<CashOrder> orders = cashOrderRepository.findAll();
        for (CashOrder order : orders) {
            double price = order.getAssets().getCoast();

            if (order.getType().equals(OrderType.INCOMING))
                plus += price;
            else
                minus += price;
        }

        return plus - minus;
    }

    @Override
    public String conductInventory(LocalDateTime after, LocalDateTime before) {
        if (after == null)
            after = LocalDateTime.MAX;
        if (before == null)
            before = LocalDateTime.MIN;

        LocalDateTime finalBefore = before;
        LocalDateTime finalAfter = after;

        double ordersSum = cashOrderRepository.findAll().stream()
                .filter(order -> order.getAssets().getCreateDate().isBefore(finalAfter)
                        && order.getAssets().getCreateDate().isAfter(finalBefore))
                .filter(order -> order.getAssets()
                        .getEquipmentStatus().equals(EquipmentStatus.IN_STOCK))
                .mapToDouble(order -> order.getAssets().getCoast())
                .sum();

        double assetsSum = assetsService.getAllAssets().stream()
                .filter(assets -> assets.getCreateDate().isBefore(finalAfter)
                        && assets.getCreateDate().isAfter(finalBefore))
                .mapToDouble(Assets::getCoast)
                .sum();

        double difference = assetsSum - ordersSum;
        if (difference < 0)
            return "Во время проведения инветаризации обнаружена недостача: " + difference + " р.";
        else if (difference > 0)
            return "Во время проведения инветаризации обнаружены не оформленные материалы и оборудование на общую сумму: " + difference + " р.";
        else
            return "Инвентаризация прошла успешно! Обнаружено имущества и материалов на общую сумму: " + assetsSum + " р.";
    }

    @Override
    public List<InventoryPerMonthStatDto> conductInventoryPerMonth() {
        List<InventoryPerMonthStatDto> result = new ArrayList<>();

        List<CashOrder> cashOrders = cashOrderRepository.findAll().stream()
                .sorted((Comparator.comparing(CashOrder::getDateTime)))
                .toList();

        for (var order : cashOrders) {
            result.add(new InventoryPerMonthStatDto(order.getDateTime(), cashOrderRepository.findAll().stream()
                    .filter(o -> o.getDateTime().isBefore(order.getDateTime()))
                    .mapToDouble(o -> o.getAssets().getCoast())
                    .sum()));

        }

        return result;
    }

    @Override
    public double calculateIncomeForPeriod(LocalDateTime after, LocalDateTime before) {
        double plus = 0.0;
        double minus = 0.0;

        Iterable<CashOrder> orders = cashOrderRepository.findAllByDateTimeAfterAndDateTimeBefore(after, before);
        for (CashOrder order : orders) {
            double price = order.getAssets().getCoast();

            if (order.getType().equals(OrderType.INCOMING))
                plus += price;
            else
                minus += price;
        }

        return plus - minus;
    }

    @Override
    public double getTrafficPerLastMonth(Long beforeLag, Long afterLag) {

        return cashOrderRepository.findAll().stream()
                .filter(order -> order.getDateTime().isBefore(LocalDateTime.now().minusMonths(beforeLag))
                        && order.getDateTime().isAfter(LocalDateTime.now().minusMonths(afterLag)))
                .mapToDouble(order -> order.getAssets().getCoast())
                .sum();
    }

    @Override
    public double getPercentDifferenceBetweenLastAndCurrentMonth() {

        double lastMonthTraffic = getTrafficPerLastMonth(0L, 1L);
        double preLastMonthTraffic = getTrafficPerLastMonth(1L, 2L);

        return preLastMonthTraffic / (lastMonthTraffic + preLastMonthTraffic);
    }


    public CashOrder joinEntities(CashOrder cashOrder) {

        Assets assets = assetsService.getAssetsById(cashOrder.getAssets().getId());

        return cashOrder.setAssets(assets);
    }
}
