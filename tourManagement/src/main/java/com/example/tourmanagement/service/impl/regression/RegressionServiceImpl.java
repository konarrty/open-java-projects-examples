package com.example.tourmanagement.service.impl.regression;

import com.example.tourmanagement.model.ComputedModel;
import com.example.tourmanagement.dto.stat.RegressionModelDTO;
import com.example.tourmanagement.mapper.regression.RegressionMapper;
import com.example.tourmanagement.model.entity.Country;
import com.example.tourmanagement.service.regression.CountryService;
import com.example.tourmanagement.service.regression.RegressionService;
import com.example.tourmanagement.facade.RegressionFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegressionServiceImpl implements RegressionService {

    private final CountryService countryService;
    private final RegressionFacade regressionFacade;
    private final RegressionMapper regressionMapper;

    @Override
    public RegressionModelDTO deduceCorrelationOfWeatherAndTouristTraffic(String name) {

        Country country = countryService.getByName(name);
        ComputedModel result = regressionFacade.compute(country);

        return regressionMapper.toDTO(result);
    }
}
