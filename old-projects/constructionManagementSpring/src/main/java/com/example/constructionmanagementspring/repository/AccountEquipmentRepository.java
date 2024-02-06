package com.example.constructionmanagementspring.repository;

import com.example.constructionmanagementspring.model.AccountEquipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountEquipmentRepository extends JpaRepository<AccountEquipment, Long> {
    Optional<AccountEquipment> findByEquipmentIdAndEmployeeId(Long equipmentId, Long workerId);
}
