package com.nk2.unityDoServices.Validators.Constraints;

import com.nk2.unityDoServices.DTOs.CreateNewActivityDTO;
import com.nk2.unityDoServices.Services.ActivityServices;
import com.nk2.unityDoServices.Validators.Validators.ActivityDateValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;

public class ActivityDateValidatorConstraint implements ConstraintValidator<ActivityDateValidator, CreateNewActivityDTO> {

    @Autowired
    ActivityServices activityServices;

    @Override
    public void initialize(ActivityDateValidator constraintAnnotation) {
    }

    @Override
    public boolean isValid(CreateNewActivityDTO activity, ConstraintValidatorContext cxt) {
        try {
            System.out.println("do ActivityDateValidatorConstraint");
            Instant activityDate = activityServices.convertDateTimeInstant(activity.getActivityDate());
            System.out.println("activityDate = "+activityDate);
            Instant activityEndDate = activityServices.convertDateTimeInstant(activity.getActivityEndDate());
            System.out.println("activityEndDate = "+activityEndDate);
            int instantComparison = activityDate.compareTo(activityEndDate);
            System.out.println("instantComparison = "+instantComparison);
            System.out.println("activityDate.isAfter(activityEndDate) = "+activityDate.isAfter(activityEndDate));
            System.out.println("activityDate.isBefore(activityEndDate) = "+activityDate.isBefore(activityEndDate));
            if (activityDate.isAfter(activityEndDate)) {

                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
    }
}