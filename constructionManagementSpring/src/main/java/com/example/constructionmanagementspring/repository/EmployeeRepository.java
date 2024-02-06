package com.example.constructionmanagementspring.repository;

import com.example.constructionmanagementspring.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
