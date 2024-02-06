package com.example.kpimanagment.validator;

import com.example.kpimanagment.validator.impl.PlanAndActualFormatValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PlanAndActualFormatValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PlanAndActualFormatConstraint {

    String message() default "Поля 'Факт' и 'План' имеют разный формат данных";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
