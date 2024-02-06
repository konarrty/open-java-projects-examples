package com.example.kpimanagment.controller;

import com.example.kpimanagment.model.Employee;
import com.example.kpimanagment.model.Mark;
import com.example.kpimanagment.model.Period;
import com.example.kpimanagment.model.Target;
import com.example.kpimanagment.service.EmployeeService;
import com.example.kpimanagment.service.MarkService;
import com.example.kpimanagment.service.PeriodService;
import com.example.kpimanagment.service.TargetService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/marks")
@RequiredArgsConstructor
public class MarkController {

    private final MarkService markService;

    @PreAuthorize("hasAnyRole('ADMIN','CURATOR')")
    @GetMapping("/showList")
    public String getAllMark(Model model, @ModelAttribute Employee employee) {


        List<Mark> markList = markService.getAllMark();

        model.addAttribute("marks", markList);

        return "mark/show";
    }

    @PreAuthorize("hasAnyRole('ADMIN','CURATOR') ")
    @PostMapping("/create")
    public String createMark(@ModelAttribute Mark mark, Model model) {

        model.addAttribute("mark", markService.createMark(mark));

        return "mark/tableRawContent";
    }

    @PreAuthorize("hasAnyRole('ADMIN','CURATOR') ")
    @PutMapping("/edit/{id}")
    public String putMark(@ModelAttribute Mark mark, @PathVariable Long id, Model model) {

        model.addAttribute("mark", markService.putMark(mark, id));

        return "mark/tableRawContent";

    }

    @PreAuthorize("hasAnyRole('ADMIN','CURATOR') ")
    @PatchMapping("/edit/{id}")
    public String patchTarget(@ModelAttribute Mark mark, @PathVariable Long id) {

        markService.patchMark(mark, id);

        return "redirect:../showList";
    }

    @PreAuthorize("hasAnyRole('ADMIN','CURATOR')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public String deleteMark(@PathVariable Long id) {

        markService.deleteMark(id);

        return "redirect:../showList";
    }

    @PreAuthorize("hasAnyRole('ADMIN','CURATOR')")
    @GetMapping("/{id}")
    public Mark getMarkById(@PathVariable Long id) {

        return markService.getMarkById(id);
    }
}
