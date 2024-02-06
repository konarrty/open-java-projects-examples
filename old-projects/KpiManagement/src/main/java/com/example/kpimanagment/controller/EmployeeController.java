package com.example.kpimanagment.controller;

import com.example.kpimanagment.enums.ERole;
import com.example.kpimanagment.model.Employee;
import com.example.kpimanagment.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/showList")
    public String getAllEmployee(Model model) {

        List<Employee> employeeList = employeeService.getAllEmployee();

        model.addAttribute("employees", employeeList);
        model.addAttribute("roles", ERole.values());

        return "employee/table";
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/add")
    public String addEmployee(Model model) {
        model.addAttribute("roles", ERole.values());

        return "employee/tableRawContentEmpty";
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public String createEmployee(@ModelAttribute @Valid Employee employee, Model model) {


        employeeService.createEmployee(employee);
        model.addAttribute("roles", ERole.values());

        return "employee/tableRawContent";
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/edit/{id}")
    public String patchEmployee(@ModelAttribute Employee employees, @PathVariable Long id, Model model) {

        model.addAttribute("employee", employeeService.patchEmployee(employees, id));
        model.addAttribute("roles", ERole.values());

        return "employee/tableRawContent";
    }

    @PreAuthorize("#id != @userServiceImpl.findByPrincipal(#principal).employee.id && hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("delete/{id}")
    public void deleteEmployee(@PathVariable Long id) {

        employeeService.deleteEmployee(id);

    }
}
