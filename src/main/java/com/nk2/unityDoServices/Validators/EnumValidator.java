package com.nk2.unityDoServices.Validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;

import static java.lang.annotation.ElementType.*;

@Documented
@Constraint(validatedBy = EnumValidatorConstraint.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@ReportAsSingleViolation
public @interface EnumValidator {
    Class<? extends Enum<?>> enumClass();
    String message() default "must be any of enum {enumClass}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}