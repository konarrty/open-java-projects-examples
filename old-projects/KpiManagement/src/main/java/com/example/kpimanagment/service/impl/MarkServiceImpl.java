package com.example.kpimanagment.service.impl;

import com.example.kpimanagment.exception.NoSuchEntityException;
import com.example.kpimanagment.exception.ServerLogicException;
import com.example.kpimanagment.model.Employee;
import com.example.kpimanagment.model.Mark;
import com.example.kpimanagment.model.Period;
import com.example.kpimanagment.model.Target;
import com.example.kpimanagment.repository.MarkRepository;
import com.example.kpimanagment.service.MarkService;
import com.example.kpimanagment.service.UserService;
import com.example.kpimanagment.utils.ExecutionIndexCalculator;
import com.example.kpimanagment.utils.PeriodUtils;
import com.example.kpimanagment.utils.ReflectionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MarkServiceImpl implements MarkService {

    private final MarkRepository markRepository;

    private final UserService userService;

    private final ReflectionUtils reflectionUtils;
    private final PeriodUtils periodUtils;
    private final ExecutionIndexCalculator executionIndexCalculator;

    @Override
    public List<Mark> getAllMark() {

        return markRepository.findAll();
    }


    @Override
    public double calculateKpi(Employee employee, Period period) {

        List<Mark> marks = markRepository.findAllMarkByTargetPeriodIdAndTargetEmployeeId(period.getId(), employee.getId());

        BigDecimal result = BigDecimal.valueOf(marks.stream().mapToDouble(this::calculate).sum());
        result = result.setScale(2, RoundingMode.HALF_UP);

        return result.doubleValue();

    }

    @Override
    public double calculateKpiPerPeriodAndCuratorPrincipal(Employee employee, Period period, Principal principal) {
        Employee curator = userService.findByPrincipal(principal).getEmployee();

        List<Mark> marks = markRepository.findAllMarkByTargetEmployeeIdAndTargetPeriodIdAndTargetCurator(
                employee.getId(),
                period.getId(),
                curator
        );

        BigDecimal result = BigDecimal.valueOf(marks.stream().mapToDouble(this::calculate).sum());
        result = result.setScale(2, RoundingMode.HALF_UP);

        return result.doubleValue();

    }

    @Override
    public double calculate(Mark mark) {

        Target target = mark.getTarget();

        return executionIndexCalculator.calculateIndex(target) * target.getWeight();
    }

    @Override
    public Mark createMark(Mark mark) {

        if (!periodUtils.isBeforeDate(mark.getTarget().getPeriod(), LocalDate.now()))
            throw new ServerLogicException("Оценить выполнение задачи можно не ранее наступления соответсвующего периода");

        return markRepository.save(mark);
    }

    @Override
    public Mark putMark(Mark newMark, Long markId) {
        Mark mark = getMarkById(markId);

        newMark.setId(mark.getId());

        return markRepository.save(newMark);

    }

    @Override
    public void patchMark(Mark newMark, Long markId) {

        Mark mark = getMarkById(markId);
        mark = reflectionUtils.merge(mark, newMark);

        markRepository.save(mark);

    }

    @Override
    public void deleteMark(Long id) {
        markRepository.deleteById(id);
    }

    @Override
    public Mark getMarkById(Long id) {
        return markRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException("Цель не найдена!"));
    }

}
