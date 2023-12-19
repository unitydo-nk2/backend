package com.nk2.unityDoServices.Validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ActivityDateValidatorConstraint.class)
@Documented
public @interface ActivityDateValidator {
    String message() default "Invalid activity date!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
