package com.example.kpimanagment.validator;

import com.example.kpimanagment.validator.impl.PlanFormatValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PlanFormatValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PlanFormatConstraint {

    String message() default "Поле 'План' имеет некорректный формат данных";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
