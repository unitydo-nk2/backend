package com.nk2.unityDoServices.Validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RegistrationEndDateValidatorConstraint.class)
@Documented
public @interface RegistrationEndDateValidator {
    String message() default "Invalid registration end date!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
