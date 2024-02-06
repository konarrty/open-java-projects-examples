package com.example.kpimanagment.controller;

import com.example.kpimanagment.model.Employee;
import com.example.kpimanagment.model.PrivilegeRead;
import com.example.kpimanagment.model.PrivilegeWrite;
import com.example.kpimanagment.model.User;
import com.example.kpimanagment.service.EmployeeService;
import com.example.kpimanagment.service.PeriodService;
import com.example.kpimanagment.service.PrivilegeService;
import com.example.kpimanagment.service.UserService;
import com.example.kpimanagment.service.cache.PrivilegeCacheService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.security.Principal;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/privileges")
@RequiredArgsConstructor
public class PrivilegeController {

    private final PrivilegeService privilegeService;
    private final PrivilegeCacheService sessionService;
    private final EmployeeService employeeService;
    private final UserService userService;
    private final PeriodService periodService;


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/showList")
    public String getAllPrivilege(Model model, @ModelAttribute("employee") Employee employee) {

        sessionService.clearCache(employee.getId(), userService.findByPrincipal(SecurityContextHolder.getContext().getAuthentication()).getEmployee().getId());
        model.addAttribute("privileges", privilegeService.getAllPrivilegeByEmployeeId(employee.getId()));
        model.addAttribute("periods", periodService.getAllPeriod());
        model.addAttribute("employees", employeeService.getAllEmployeesExceptAdminRole());

        return "privilege/show";
    }

    @PreAuthorize("hasRole('CURATOR')")
    @GetMapping("/my")
    public String getMyPrivileges(Model model, Principal principal) {

        User userCurator = userService.findByPrincipal(principal);
        Employee curator = userCurator.getEmployee();

        model.addAttribute("privileges", userCurator.getPrivileges());
        model.addAttribute("periods", periodService.getAllPeriod());
        model.addAttribute("employee", curator);

        return "privilege/showMe";
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public String getOnlyPrivileges(Model model, @ModelAttribute("employee") Employee employee,
                                    @RequestParam Class<?> type) {
        var principal = SecurityContextHolder.getContext().getAuthentication();
        sessionService.clearCache(employee.getId(),
                userService.findByPrincipal(principal).getEmployee().getId());

        model.addAttribute("privileges",
                privilegeService.getAllPrivilegeByTypeAndEmployeeId(type, employee.getId()));
        model.addAttribute("employees",
                employeeService.getAllEmployeesWithCuratorRole());
        model.addAttribute("periods",
                periodService.getAllPeriod());

        return "privilege/tableRaw";
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/add")
    public String showAddPage(Model model, @ModelAttribute Employee employee, @RequestParam Class<?> type) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        model.addAttribute("privilege", type.getDeclaredConstructor().newInstance());
        model.addAttribute("employees", employeeService.getAllEmployeesWithCuratorRole());

        return "privilege/tableRawEmpty";
    }

    @PreAuthorize("(@privilegeFilterUtils.notAdmin(#privilege) || privilegeFilterUtils.notMe(#privilege)) && hasRole('ADMIN')")
    @PostMapping("/create")
    public String createPrivilege(@ModelAttribute @Valid PrivilegeWrite privilege, Model model) throws ExecutionException, InterruptedException {

        model.addAttribute("employees", employeeService.getAllEmployeesWithCuratorRole());
        model.addAttribute("periods", periodService.getAllPeriod());
        model.addAttribute("cache", privilegeService.createPrivilegeInCache(privilege));

        return "privilege/tableRawCacheContent";

    }

    @PreAuthorize("(@privilegeFilterUtils.notAdmin(#privilege) || privilegeFilterUtils.notMe(#privilege)) && hasRole('ADMIN')")
    @PostMapping("/createWithoutCache")
    public String createPrivilegeRead(@ModelAttribute @Valid PrivilegeRead privilege, Model model) {

        model.addAttribute("employees", employeeService.getAllEmployeesWithCuratorRole());
        model.addAttribute("periods", periodService.getAllPeriod());
        model.addAttribute("privilege", privilegeService.createPrivilege(privilege));

        return "privilege/tableRawContent";

    }

    @PreAuthorize("(@privilegeFilterUtils.notAdmin(#privilege) || privilegeFilterUtils.notMe(#privilege)) && hasRole('ADMIN')")
    @PatchMapping("/edit/{id}")
    public String patchPrivilege(@ModelAttribute PrivilegeWrite privilege, @PathVariable Long id, Model model) throws ExecutionException, InterruptedException {

        model.addAttribute("employees", employeeService.getAllEmployeesWithCuratorRole());
        model.addAttribute("periods", periodService.getAllPeriod());
        model.addAttribute("cache", privilegeService.patchPrivilegeInCache(privilege, id));

        return "privilege/tableRawCacheContent";
    }

    @PreAuthorize("(@privilegeFilterUtils.notAdmin(#privilege) || privilegeFilterUtils.notMe(#privilege)) && hasRole('ADMIN')")
    @PatchMapping("/editWithoutCache/{id}")
    public String patchPrivilegeRead(@ModelAttribute PrivilegeRead privilege, @PathVariable Long id, Model model) {

        model.addAttribute("employees", employeeService.getAllEmployeesWithCuratorRole());
        model.addAttribute("periods", periodService.getAllPeriod());
        model.addAttribute("privilege", privilegeService.patchPrivilege(privilege, id));

        return "privilege/tableRawContent";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/save")
    public void save(@RequestParam Long employeeId) {

        privilegeService.saveChangesFromCache(employeeId);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/deleteFromCache/{id}")
    public void deletePrivilegeOnlyFromCache(@PathVariable Long id, @RequestParam Long employeeId) {

        privilegeService.deletePrivilegeOnlyFromCache(id, employeeId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{id}")
    public void deletePrivilege(@PathVariable Long id) {

        privilegeService.deletePrivilegeFromCache(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/deletePermanently/{id}")
    public void deletePrivilegePermanently(@PathVariable Long id) {

        privilegeService.deletePrivilege(id);
    }


}
