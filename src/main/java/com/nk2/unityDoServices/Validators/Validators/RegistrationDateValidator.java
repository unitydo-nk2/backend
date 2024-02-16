package com.nk2.unityDoServices.Validators.Validators;

import com.nk2.unityDoServices.Validators.Constraints.RegistrationDateValidatorConstraint;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RegistrationDateValidatorConstraint.class)
@Documented
public @interface RegistrationDateValidator {
    String message() default "Invalid registration date!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
