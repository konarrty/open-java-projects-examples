package com.example.constructionmanagementspring.service;

import com.example.constructionmanagementspring.model.AccountEquipment;

import java.security.Principal;
import java.util.List;

public interface AccountEquipmentService {
    List<AccountEquipment> getAllAccountEquipments(int page);

    AccountEquipment getAccountEquipmentById(Long id);

    AccountEquipment createAccountEquipment(AccountEquipment accountEquipment);

    AccountEquipment putAccountEquipment(AccountEquipment accountAccountEquipments, Long accountAccountEquipmentsId);

    void deleteAccountEquipment(Long id);

    AccountEquipment findByEquipmentIdAndWorkerId(AccountEquipment newAccountEquipment);

    AccountEquipment handOverEquipment(AccountEquipment newAccountEquipment);


}
