package com.example.constructionmanagementspring.service.impl;

import com.example.constructionmanagementspring.exception.NoSuchEntityException;
import com.example.constructionmanagementspring.model.AccountEquipment;
import com.example.constructionmanagementspring.model.Employee;
import com.example.constructionmanagementspring.model.Equipment;
import com.example.constructionmanagementspring.repository.AccountEquipmentRepository;
import com.example.constructionmanagementspring.service.AccountEquipmentService;
import com.example.constructionmanagementspring.service.EquipmentService;
import com.example.constructionmanagementspring.service.qr.QRCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountEquipmentServiceImpl implements AccountEquipmentService {

    private final AccountEquipmentRepository accountEquipmentRepository;

    private final QRCodeService qrCodeService;
    private final EquipmentService equipmentService;



    @Override
    public List<AccountEquipment> getAllAccountEquipments(int page) {

        return accountEquipmentRepository.findAll(
                        PageRequest.of(page - 1, 5))
                .getContent();
    }

    @Override
    public AccountEquipment createAccountEquipment(AccountEquipment accountEquipment) {

        if (accountEquipment.getEquipment().getAccountEquipments().stream()
                .anyMatch(AccountEquipment::isActual))
            return null;

        equipmentService.updateQr(
                generateQrForEquipment(accountEquipment),
                accountEquipment.getEquipment());

        return accountEquipmentRepository.save(accountEquipment);
    }

    @Override
    public AccountEquipment putAccountEquipment(AccountEquipment newAccountEquipment, Long accountEquipmentId) {

        AccountEquipment accountEquipment = accountEquipmentRepository
                .findById(accountEquipmentId)
                .orElseThrow(() ->
                        new NoSuchEntityException("Отчёт не найден!"));

        newAccountEquipment.setId(accountEquipment.getId());

        return accountEquipmentRepository.save(newAccountEquipment);
    }

    @Override
    public void deleteAccountEquipment(Long id) {
        if (!accountEquipmentRepository.existsById(id))
            throw new NoSuchEntityException("Отчёт не найден!");

        accountEquipmentRepository.deleteById(id);
    }

    @Override
    public AccountEquipment getAccountEquipmentById(Long id) {

        return accountEquipmentRepository
                .findById(id)
                .orElseThrow(() ->
                        new NoSuchEntityException("Отчёт не найден!"));
    }

    @Override
    public AccountEquipment handOverEquipment(AccountEquipment newAccountEquipment) {

        AccountEquipment accountEquipment = accountEquipmentRepository.findByEquipmentIdAndEmployeeId(newAccountEquipment.getEquipment().getId()
                , newAccountEquipment.getEmployee().getId()).orElseThrow(() ->
                new NoSuchEntityException("Данное оборудование у работника не найдено"));

        accountEquipment.setActual(false);
        accountEquipment.setLastActualModifiedDate(LocalDateTime.now());

        return accountEquipmentRepository.save(accountEquipment);
    }

    @Override
    public AccountEquipment findByEquipmentIdAndWorkerId(AccountEquipment newAccountEquipment) {

        return accountEquipmentRepository.findByEquipmentIdAndEmployeeId(newAccountEquipment.getEquipment().getId()
                , newAccountEquipment.getEmployee().getId()).orElseThrow();
    }

    public String generateQrForEquipment(AccountEquipment accountEquipment) {
        Equipment equipment = accountEquipment.getEquipment();
        Employee employee = accountEquipment.getEmployee();

        return qrCodeService
                .createQRCode("Оборудование: " + equipment +
                        "\nВ данный момент находится у: " + employee);
    }


}
