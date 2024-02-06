package com.example.kpimanagment.service.impl;

import com.example.kpimanagment.enums.ERole;
import com.example.kpimanagment.exception.NoSuchEntityException;
import com.example.kpimanagment.model.Employee;
import com.example.kpimanagment.model.Period;
import com.example.kpimanagment.model.PrivilegeWrite;
import com.example.kpimanagment.model.User;
import com.example.kpimanagment.repository.EmployeeRepository;
import com.example.kpimanagment.service.EmployeeService;
import com.example.kpimanagment.service.PrivilegeService;
import com.example.kpimanagment.service.TargetService;
import com.example.kpimanagment.service.UserService;
import com.example.kpimanagment.utils.PeriodUtils;
import com.example.kpimanagment.utils.ReflectionUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final UserService userService;
    private final TargetService targetService;
    private final PrivilegeService privilegeService;

    private final ReflectionUtils reflectionUtils;
    private final PeriodUtils periodUtils;

    @Override
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    @Override
    public Set<Employee> getAllEmployeeForCuratorPrincipal(Principal principal) {
        Employee curator = userService.findByPrincipal(principal).getEmployee();

        Set<Employee> employeeSet = new HashSet<>();
        curator.getTargetsCurator().forEach(target -> employeeSet.add(target.getEmployee()));
        privilegeService.extractPrivilegeWrite(curator.getUser().getPrivileges()).forEach(privilege -> employeeSet.add(privilege.getEmployee()));

        return employeeSet;

    }

    @Override
    public List<Employee> getAllEmployeesWithCuratorRole() {

        return employeeRepository.findByUserRole(ERole.ROLE_CURATOR);
    }

    @Override
    public List<Employee> getAllEmployeesExceptAdminRole() {

        return employeeRepository.findByUserRoleNot(ERole.ROLE_ADMIN);
    }


    @Transactional
    @Override
    public Employee createEmployee(Employee employee) {
        User user = userService.getUserByIdWithLocking(employee.getUser().getId());
        if (user.getInvitationCode() == null)
            user.setInvitationCode(RandomStringUtils.randomAlphabetic(10));

        return employeeRepository.save(employee);
    }

    @Override
    public Employee putEmployee(Employee newEmployee, Long employeeId) {
        Employee employee = getEmployeeById(employeeId);

        newEmployee.setId(employee.getId());

        return employeeRepository.save(newEmployee);

    }

    @Transactional
    @Override
    public void deleteEmployee(Long id) {
        Employee employee = getEmployeeById(id);

        targetService.deleteAllTargetsByCuratorId(employee.getId());
        targetService.deleteAllTargetsByEmployeeId(employee.getId());
        privilegeService.deleteAllPrivilegesByEmployeeId(employee.getId());
        privilegeService.deleteAllPrivilegesByCuratorUserId(employee.getUser().getId());
        employeeRepository.deleteById(id);
    }

    @Override
    public Employee patchEmployee(Employee newEmployee, Long id) {
        Employee employee = getEmployeeById(id);

        employee = reflectionUtils.merge(employee, newEmployee);

        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(Long id) {

        return employeeRepository.findById(id).orElseThrow(() ->
                new NoSuchEntityException("Сотрудник не найден!"));
    }

    @Override
    public BigDecimal getEmployeeParticipationRateForCurator(Employee employee, Employee curator, Period period) {
        double result;
        if (!periodUtils.isBeforeLocaleDate(period))
            result = privilegeService.extractPrivilegeWrite(curator.getUser().getPrivileges())
                    .stream()
                    .filter(privilege -> privilege.getEmployee().equals(employee))
                    .findFirst()
                    .map(PrivilegeWrite::getParticipationRate)
                    .orElse(0.0);
        else {
            result = targetService.sumRate(curator, employee, period);
        }

        BigDecimal roundingResult = new BigDecimal(result);
        roundingResult = roundingResult.setScale(2, RoundingMode.HALF_UP);

        return roundingResult;

    }


}
