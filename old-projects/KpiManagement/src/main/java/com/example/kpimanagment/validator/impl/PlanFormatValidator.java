package com.example.kpimanagment.validator.impl;

import com.example.kpimanagment.model.Target;
import com.example.kpimanagment.validator.PlanFormatConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.math.NumberUtils;

public class PlanFormatValidator implements ConstraintValidator<PlanFormatConstraint, Object> {

    @Override
    public void initialize(PlanFormatConstraint planFormatConstraint) {
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        if (o instanceof Target target) {
            String plan = target.getPlan();
            if (plan.indexOf('%') == plan.length() - 1) {
                plan = plan.replace("%", "");

            }
            return NumberUtils.isCreatable(plan) && plan.chars().skip(plan.length() - 1).anyMatch(Character::isDigit);
        }

        return false;
    }


}