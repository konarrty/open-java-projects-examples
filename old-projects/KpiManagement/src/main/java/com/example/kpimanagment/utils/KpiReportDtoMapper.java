package com.example.kpimanagment.utils;

import com.example.kpimanagment.dto.KpiReport;
import com.example.kpimanagment.model.Mark;
import com.example.kpimanagment.model.Target;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@RequiredArgsConstructor
public class KpiReportDtoMapper {

    private final ExecutionIndexCalculator executionIndexCalculator;

    public KpiReport mapToDto(Target target) {

        Mark mark = target.getMark();

        double executionIndex = 0, weightFactor = 0;

        if (mark != null) {

            executionIndex = executionIndexCalculator.calculateIndex(target);
            weightFactor = executionIndex * target.getWeight();
        }
        BigDecimal bigDecimalWeightFactor = new BigDecimal(weightFactor);
        bigDecimalWeightFactor = bigDecimalWeightFactor.setScale(2, RoundingMode.HALF_UP);

        return KpiReport.builder()
                .target(target)
                .executionIndex(executionIndex)
                .weightFactor(bigDecimalWeightFactor.doubleValue())
                .build();
    }

}
