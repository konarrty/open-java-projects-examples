package com.example.constructionmanagementspring.service.impl;

import com.example.constructionmanagementspring.exception.NoSuchEntityException;
import com.example.constructionmanagementspring.model.Employee;
import com.example.constructionmanagementspring.repository.EmployeeRepository;
import com.example.constructionmanagementspring.repository.RoleRepository;
import com.example.constructionmanagementspring.service.EmployeeService;
import com.example.constructionmanagementspring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.BeanDefinitionDsl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final RoleRepository roleRepository;

    @Override
    public List<Employee> getAllEmployees(int page) {

        return employeeRepository.findAll(
                        PageRequest.of(page, 5))
                .getContent();
    }

    @Override
    public Employee createEmployee(Employee employee) {
        employee.getUser().setRoles(List.of(roleRepository.findByName("ROLE_EMPLOYEE")));

        return employeeRepository.save(employee);
    }

    @Override
    public Employee putEmployee(Employee newEmployee, Long employeeId) {

        Employee employee = employeeRepository
                .findById(employeeId)
                .orElseThrow(() ->
                        new NoSuchEntityException("Номенклатурная группа  не найден!"));

        newEmployee.setId(employee.getId());

        return employeeRepository.save(newEmployee);
    }

    @Override
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id))
            throw new NoSuchEntityException("Номенклатурная группа  не найден!");

        employeeRepository.deleteById(id);
    }

    @Override
    public Employee getEmployeeById(Long id) {

        return employeeRepository
                .findById(id)
                .orElseThrow(() ->
                        new NoSuchEntityException("Номенклатурная группа не найден!"));
    }


}
