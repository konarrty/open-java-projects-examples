package com.example.kpimanagment.validator.impl;

import com.example.kpimanagment.exception.ServerLogicException;
import com.example.kpimanagment.model.Mark;
import com.example.kpimanagment.validator.PlanAndActualFormatConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.math.NumberUtils;

public class PlanAndActualFormatValidator implements ConstraintValidator<PlanAndActualFormatConstraint, Object> {

    @Override
    public void initialize(PlanAndActualFormatConstraint planAndActualFormatConstraint) {
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        if (o instanceof Mark mark) {
            String plan = mark.getTarget().getPlan();
            String actual = mark.getActual();
            if (plan.indexOf('%') == plan.length() - 1 && actual.indexOf('%') == actual.length() - 1) {
                plan = plan.replace("%", "");
                actual = actual.replace("%", "");

            }
            return NumberUtils.isCreatable(plan) && NumberUtils.isCreatable(actual)
                    && plan.chars().skip(plan.length() - 1).anyMatch(Character::isDigit)
                    && actual.chars().skip(actual.length() - 1).anyMatch(Character::isDigit);
        }

        return false;
    }


}