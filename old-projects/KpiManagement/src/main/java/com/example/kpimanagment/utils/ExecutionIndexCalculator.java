package com.example.kpimanagment.utils;

import com.example.kpimanagment.dto.EmployeeKPIDto;
import com.example.kpimanagment.exception.ServerLogicException;
import com.example.kpimanagment.model.Employee;
import com.example.kpimanagment.model.Target;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

@Component
public class ExecutionIndexCalculator {

    public double calculateIndex(Target target) {

        String plan = target.getPlan();
        String actual = target.getMark().getActual();
        double planDouble, actualDouble = 0;

        if (plan.indexOf('%') == plan.length() - 1 && actual.indexOf('%') == actual.length() - 1) {
            plan = plan.replace("%", "");
            actual = actual.replace("%", "");
        }
        if (NumberUtils.isCreatable(plan) && NumberUtils.isCreatable(actual)
                && plan.chars().skip(plan.length() - 1).anyMatch(Character::isDigit)
                && actual.chars().skip(actual.length() - 1).anyMatch(Character::isDigit)) {
            planDouble = NumberUtils.createDouble(plan);
            actualDouble = NumberUtils.createDouble(actual);
        } else
            throw new ServerLogicException("Неверный формат оценок показателей работы!");


        double result = actualDouble / planDouble * 100;

        BigDecimal bigDecimalResult = new BigDecimal(result);
        bigDecimalResult = bigDecimalResult.setScale(2, RoundingMode.HALF_UP);

        return (result > 150) ? 150 : bigDecimalResult.doubleValue();
    }

}
