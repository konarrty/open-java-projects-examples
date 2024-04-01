package com.example.astontaskspringrestful.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemperatureDTO {

    @JsonProperty(value = "temperature", access = JsonProperty.Access.READ_ONLY)
    private Double temperature;

    @JsonCreator
    public TemperatureDTO(@JsonProperty("current") Map<String, Object> current) {
        this.temperature = (Double) current.get("temperature_2m");
    }

    @Override
    public String toString() {
        return String.valueOf(temperature);
    }
}
