package com.example.kpimanagment.converter;

import com.example.kpimanagment.model.Indicator;
import com.example.kpimanagment.service.IndicatorService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class IndicatorConverter implements Converter<String, Indicator> {

    private final IndicatorService indicatorService;

    @Override
    public Indicator convert(@NotNull String source) {
        Indicator indicator = indicatorService.getIndicatorByName(source);

        return Objects.requireNonNullElseGet(indicator, () -> new Indicator(source));
    }
}