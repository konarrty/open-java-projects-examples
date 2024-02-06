package com.example.kpimanagment.controller;

import com.example.kpimanagment.dto.KpiReport;
import com.example.kpimanagment.model.*;
import com.example.kpimanagment.service.*;
import com.example.kpimanagment.service.cache.TargetCacheService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/targets")
@RequiredArgsConstructor
public class TargetController {

    private final TargetService targetService;
    private final EmployeeService employeeService;
    private final TargetCacheService sessionService;
    private final UserService userService;
    private final PrivilegeService privilegeService;
    private final MarkService markService;
    private final PeriodService periodService;
    private final IndicatorService indicatorService;

    @GetMapping("/kpi")
    public String getAllKpi(@ModelAttribute Period period,
                            @ModelAttribute Employee employee,
                            Principal principal,
                            Model model) {

        Employee curator = userService.findByPrincipal(principal).getEmployee();
        sessionService.clearCache(employee.getId(), period.getId(),curator.getId());

        model.addAttribute("employees", employeeService.getAllEmployeeForCuratorPrincipal(principal));
        model.addAttribute("periods", periodService.getAllPeriod());
        model.addAttribute("curator", curator);

        return "kpi";
    }

    @GetMapping("/list")
    public String getOnlyTargets(@ModelAttribute Period period, @ModelAttribute Employee employee,
                                 Model model, Principal principal) {

        Employee curator = userService.findByPrincipal(principal).getEmployee();
        sessionService.clearCache(employee.getId(), period.getId(),curator.getId());

        model.addAttribute("kpi", markService.calculateKpiPerPeriodAndCuratorPrincipal(employee, period, principal));
        model.addAttribute("targets", targetService.getAllTargetsByPrincipalAndPeriodAndEmployee(principal, period.getId(), employee.getId()));
        model.addAttribute("participationRate", employeeService.getEmployeeParticipationRateForCurator(employee, curator, period));
        model.addAttribute("indicators", indicatorService.getAllIndicator());
        model.addAttribute("curator", curator);

        return "target/tableRaw";
    }


    @GetMapping("/add")
    public String showAddPage(@ModelAttribute Employee employee, @ModelAttribute Period period, Principal principal, Model model) {

        model.addAttribute("curator", userService.findByPrincipal(principal).getEmployee());

        return "target/tableRawEmpty";
    }

    @PostMapping("/create")
    public String createTarget(@ModelAttribute @Valid Target target, Model model, Principal principal) {

        model.addAttribute("cache", targetService.createTargetInCache(target));
        model.addAttribute("curator", userService.findByPrincipal(principal).getEmployee());
        model.addAttribute("indicators", indicatorService.getAllIndicator());

        return "target/tableRawCacheContent";

    }

    @PatchMapping("/edit/{id}")
    public String patchTarget(@ModelAttribute Target target, @PathVariable Long id, Model model, Principal principal) {

        model.addAttribute("indicators", indicatorService.getAllIndicator());
        model.addAttribute("curator", userService.findByPrincipal(principal).getEmployee());
        model.addAttribute("cache", targetService.patchTargetInCache(target, id));

        return "target/tableRawCacheContent";
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/save")
    public void save(@RequestParam Long employeeId, @RequestParam Long periodId) {

        targetService.saveChangesFromCache(employeeId, periodId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/deleteFromCache/{targetId}")
    public void deleteTargetOnlyFromCache(@PathVariable Long targetId, @RequestParam Long employeeId, @RequestParam Long periodId) {

        targetService.deleteTargetOnlyFromCache(targetId, employeeId, periodId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{targetId}")
    public void deleteTarget(@PathVariable Long targetId) {

        targetService.deleteTargetFromCache(targetId);
    }

    @GetMapping("/showList")
    public String getAllTarget(Model model) {

        List<Target> targetList = targetService.getAllTarget();
        model.addAttribute("targets", targetList);

        return "target/show";
    }

    @GetMapping("/{id}")
    public String getTargetById(Model model, @PathVariable Long id, Principal principal) {

        KpiReport report = targetService.getKpiReportByTargetId(id);
        model.addAttribute("indicators", indicatorService.getAllIndicator());
        model.addAttribute("curator", userService.findByPrincipal(principal).getEmployee());
        model.addAttribute("report", report);

        return "target/tableRawContent";
    }

    @GetMapping("/my")
    public String personalPageShow(Model model, @ModelAttribute Period period, Principal principal) {

        Employee employee = userService.findByPrincipal(principal).getEmployee();

        model.addAttribute("kpiReports", targetService.findTargetsByEmployeeAndPeriod(employee, period));
        model.addAttribute("kpi", markService.calculateKpi(employee, period));
        model.addAttribute("periods", periodService.getAllPeriod());
        model.addAttribute("employee", employee);

        return "personalArea";
    }

    @GetMapping("/checks")
    public String personalPageShow(Model model,
                                   @ModelAttribute Period period,
                                   @ModelAttribute Employee employee,
                                   Principal principal) {

        Employee curator = userService.findByPrincipal(principal).getEmployee();

        model.addAttribute("kpiReports", targetService.findTargetsByEmployeeAndPeriod(curator, period));
        model.addAttribute("kpi", markService.calculateKpi(employee, period));
        model.addAttribute("periods", periodService.getAllPeriod());
        model.addAttribute("employees", privilegeService.extractPrivilegeRead(curator.getUser().getPrivileges()).stream().map(Privilege::getEmployee).collect(Collectors.toSet()));

        return "kpiForChecker";
    }

    @GetMapping("/checksList")
    public String checkPageList(Model model,
                                @ModelAttribute Period period,
                                @ModelAttribute Employee employee,
                                Principal principal) {

        Employee curator = userService.findByPrincipal(principal).getEmployee();

        model.addAttribute("kpiReports", targetService.findTargetsByEmployeeAndPeriod(employee, period));
        model.addAttribute("kpi", markService.calculateKpi(employee, period));
        model.addAttribute("periods", periodService.getAllPeriod());
        model.addAttribute("employee", curator);

        return "personalAreaList";
    }

    @GetMapping("/myResult")
    public String personalPageList(Model model, @ModelAttribute Period period, Principal principal) {

        Employee employee = userService.findByPrincipal(principal).getEmployee();

        model.addAttribute("kpiReports", targetService.findTargetsByEmployeeAndPeriod(employee, period));
        model.addAttribute("kpi", markService.calculateKpi(employee, period));
        model.addAttribute("periods", periodService.getAllPeriod());
        model.addAttribute("employee", employee);

        return "personalAreaList";
    }

    @GetMapping("/Kpi")
    public String personalPageShow(@ModelAttribute Period period,
                                   @ModelAttribute Employee employee,
                                   Principal principal,
                                   Model model) {

        model.addAttribute("kpi", markService.calculateKpiPerPeriodAndCuratorPrincipal(employee, period, principal));

        model.addAttribute("participationRate", employeeService.getEmployeeParticipationRateForCurator(employee, userService.findByPrincipal(principal).getEmployee(), period));

        return "kpiTemplate";
    }

}
