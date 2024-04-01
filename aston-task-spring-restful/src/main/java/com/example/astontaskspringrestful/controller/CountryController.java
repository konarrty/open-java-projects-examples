package com.example.astontaskspringrestful.controller;

import com.example.astontaskspringrestful.dto.TemperatureDTO;
import com.example.astontaskspringrestful.hateoas.CountryModelAssembler;
import com.example.astontaskspringrestful.model.Country;
import com.example.astontaskspringrestful.service.CountryService;
import com.example.astontaskspringrestful.service.WeatherService;
import com.example.astontaskspringrestful.util.CollectionUtils;
import com.example.astontaskspringrestful.util.ResponseEntityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequestMapping("/api/v1/countries")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;
    private final WeatherService weatherService;
    private final CountryModelAssembler modelAssembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Country>>> getAllCountries() {

        return countryService.getAllCountries()
                .filter(CollectionUtils::isNotEmpty)
                .map(modelAssembler::toCollectionModel)
                .map(ResponseEntityUtils::noCache)
                .orElse(notFound().build());
    }

    @PostMapping
    public ResponseEntity<EntityModel<Country>> createCountry(@RequestBody Country country) {
        Country savedCountry = countryService.createCountry(country);
        EntityModel<Country> model = modelAssembler.toModel(savedCountry);

        return ResponseEntity.created(
                        model.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(model);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteCountryById(@PathVariable Long id) {

        countryService.deleteCountryById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public Country patchCountry(@RequestBody Country country, @PathVariable Long id) {

        return countryService.patchCountry(country, id);
    }

    @Operation(summary = "Получить страну по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Страна найдена",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Country.class))}),
            @ApiResponse(responseCode = "400", description = "Передан некорректный id",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Книга не найдена",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Country>> getCountryById(@PathVariable Long id) {

        return countryService.getCountryById(id)
                .map(modelAssembler::toModel)
                .map(model -> ResponseEntityUtils.cacheDefault(model, 60))
                .orElse(notFound().build());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/temperature")
    public ResponseEntity<TemperatureDTO> getTemperature(@PathVariable Long id) {

        return weatherService.getTemperatureByCountryId(id)
                .map(ResponseEntity::ok)
                .orElse(notFound().build());
    }
}
