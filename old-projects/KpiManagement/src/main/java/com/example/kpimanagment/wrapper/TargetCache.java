package com.example.kpimanagment.wrapper;

import com.example.kpimanagment.dto.KpiReport;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
public class TargetCache {

    @EqualsAndHashCode.Include
    private Long id;

    private KpiReport report;

}
