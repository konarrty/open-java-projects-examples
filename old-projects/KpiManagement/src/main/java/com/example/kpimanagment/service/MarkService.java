package com.example.kpimanagment.service;

import com.example.kpimanagment.model.Employee;
import com.example.kpimanagment.model.Mark;
import com.example.kpimanagment.model.Period;

import java.security.Principal;
import java.util.List;

public interface MarkService {
    List<Mark> getAllMark();

    Mark getMarkById(Long id);

    double calculateKpi(Employee employee, Period period);

    double calculateKpiPerPeriodAndCuratorPrincipal(Employee employee, Period period, Principal principal);

    double calculate(Mark mark);

    Mark createMark(Mark mark);

    Mark putMark(Mark mark, Long markId);

    void patchMark(Mark newMark, Long markId);

    void deleteMark(Long id);
}
