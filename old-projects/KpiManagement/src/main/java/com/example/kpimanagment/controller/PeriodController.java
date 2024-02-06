package com.example.kpimanagment.controller;

import com.example.kpimanagment.enums.EPeriodType;
import com.example.kpimanagment.model.Period;
import com.example.kpimanagment.service.PeriodService;
import com.example.kpimanagment.utils.PeriodUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/periods")
@RequiredArgsConstructor
public class PeriodController {

    private final PeriodService periodService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/showList")
    public String getAllPeriod(Model model) {

        List<Period> periodList = periodService.getAllPeriod();

        model.addAttribute("periods", periodList);
        model.addAttribute("types", EPeriodType.values());

        return "period/show";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/add")
    public String showAddPage(Model model) {

        model.addAttribute("types", EPeriodType.values());

        return "period/tableRawEmpty";
    }

    @PreAuthorize("hasAnyRole('CURATOR')")
    @ResponseBody
    @GetMapping("/isAfter")
    public boolean isAfter(Period period) {

        return periodService.isAfter(period);

    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/create")
    public String createPeriod(@ModelAttribute @Valid Period period, Model model) {

        model.addAttribute("period", periodService.createPeriod(period));
        model.addAttribute("types", EPeriodType.values());

        return "period/tableRawContent";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/edit/{id}")
    public String putPeriod(@ModelAttribute Period period, @PathVariable Long id
            , Model model) {

        model.addAttribute("period", periodService.putPeriod(period, id));
        model.addAttribute("types", EPeriodType.values());

        return "period/tableRawContent";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("delete/{id}")
    public void deletePeriod(@PathVariable Long id) {

        periodService.deletePeriod(id);
    }
}
