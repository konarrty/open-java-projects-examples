package com.example.kpimanagment.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@Entity
public class PrivilegeWrite extends Privilege {

    @Max(1)
    @DecimalMin(value = "0.01")
    @Column(name = "participation_rate")
    private double participationRate;

    public PrivilegeWrite() {
        super();
    }
}