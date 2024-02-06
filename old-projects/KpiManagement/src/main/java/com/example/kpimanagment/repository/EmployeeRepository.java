package com.example.kpimanagment.repository;

import com.example.kpimanagment.enums.ERole;
import com.example.kpimanagment.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByUserRole(ERole role);

    List<Employee> findByUserRoleNot(ERole role);


}
