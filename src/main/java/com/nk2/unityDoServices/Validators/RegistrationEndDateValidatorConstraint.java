package com.nk2.unityDoServices.Validators;


import com.nk2.unityDoServices.DTOs.CreateNewActivityDTO;
import com.nk2.unityDoServices.Services.ActivityServices;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;

public class RegistrationEndDateValidatorConstraint implements ConstraintValidator<RegistrationEndDateValidator, CreateNewActivityDTO> {

    @Autowired
    ActivityServices activityServices;

    @Override
    public void initialize(RegistrationEndDateValidator constraintAnnotation) {
    }

    @Override
    public boolean isValid(CreateNewActivityDTO activity, ConstraintValidatorContext cxt) {
        try {
            System.out.println("do ActivityDateValidatorConstraint");
            Instant activityDate = activityServices.convertDateTimeInstant(activity.getActivityDate());
            System.out.println("activityDate = "+activityDate);
            Instant activityEndDate = activityServices.convertDateTimeInstant(activity.getActivityEndDate());
            System.out.println("activityEndDate = "+activityEndDate);
            Instant announcementDate = activityServices.convertDateTimeInstant(activity.getAnnouncementDate());
            System.out.println("announcementDate = "+activityEndDate);
            Instant registrationEndDate = activityServices.convertDateTimeInstant(activity.getRegisterEndDate());
            System.out.println("registrationEndDate = "+registrationEndDate);
            if (registrationEndDate.isAfter(announcementDate) || registrationEndDate.isAfter(activityDate) || registrationEndDate.isAfter(activityEndDate)) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
    }
}