package com.example.kpimanagment.controller;

import com.example.kpimanagment.model.Indicator;
import com.example.kpimanagment.service.IndicatorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.util.List;

@Controller
@RequestMapping("/indicators")
@PreAuthorize("hasAnyRole('CURATOR','ADMIN')")
@RequiredArgsConstructor
public class IndicatorController {

    private final IndicatorService indicatorService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/showList")
    public String getAllIndicator(Model model) {

        model.addAttribute("indicators", indicatorService.getAllIndicator());

        return "indicator/show";
    }

    @PreAuthorize("hasAnyRole('ADMIN','CURATOR')")
    @ResponseBody
    @GetMapping("/list")
    public List<String> getAllIndicator() {

        return indicatorService.getAllIndicator().stream().map(Indicator::getName).toList();
    }

    @PreAuthorize("hasAnyRole('ADMIN','CURATOR')")
    @GetMapping("/select")
    public String getIndicatorsForSelect(Model model) {

        model.addAttribute("indicators", indicatorService.getAllIndicator());

        return "indicator/select";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/add")
    public String showAddPage(Model model) {

        model.addAttribute("indicator", new Indicator());

        return "indicator/tableRawEmpty";
    }

    @PreAuthorize("hasAnyRole('ADMIN','CURATOR')")
    @PostMapping("/create")
    public String createIndicator(@ModelAttribute @Valid Indicator indicator, Model model) {

        model.addAttribute("indicator",
                indicatorService.createIndicator(indicator));

        return "indicator/tableRawContent";

    }

    @PreAuthorize("hasAnyRole('ADMIN','CURATOR')")
    @PutMapping("/edit/{id}")
    public String putIndicator(@ModelAttribute Indicator indicator, @PathVariable Long id,
                               Model model) {

        model.addAttribute("indicator",
                indicatorService.putIndicator(indicator, id));

        return "indicator/tableRawContent";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("delete/{id}")
    public void deleteIndicator(@PathVariable Long id) {

        indicatorService.deleteIndicator(id);
    }

}
