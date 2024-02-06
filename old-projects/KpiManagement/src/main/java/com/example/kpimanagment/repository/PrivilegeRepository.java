package com.example.kpimanagment.repository;

import com.example.kpimanagment.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    @Query("SELECT p FROM Privilege p WHERE TYPE(p) = :type AND p.employee.id = :employeeId  AND p.user.id = :userId")
    List<Privilege> findOnlyPrivilegeWriteByEmployeeIdAndUserId(Class<?> type, Long employeeId, Long userId);

    List<Privilege> findAllByEmployeeId(Long employeeId);

    @Query("SELECT p FROM Privilege p WHERE TYPE(p) = :type AND p.employee.id = :employeeId")
    List<Privilege> findAllByTypeAndEmployeeId(Class<?> type, Long employeeId);

    void deleteAllByEmployeeId(Long employeeId);

    void deleteAllByUserId(Long userId);
}
