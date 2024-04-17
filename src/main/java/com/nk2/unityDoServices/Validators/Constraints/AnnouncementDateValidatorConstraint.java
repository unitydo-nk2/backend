package com.nk2.unityDoServices.Validators.Constraints;

import com.nk2.unityDoServices.DTOs.Activity.CreateNewActivityDTO;
import com.nk2.unityDoServices.Services.ActivityServices;
import com.nk2.unityDoServices.Validators.Validators.AnnouncementDateValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.LocalDateTime;

public class AnnouncementDateValidatorConstraint implements ConstraintValidator<AnnouncementDateValidator, CreateNewActivityDTO> {

    @Autowired
    ActivityServices activityServices;

    @Override
    public void initialize(AnnouncementDateValidator constraintAnnotation) {
    }

    @Override
    public boolean isValid(CreateNewActivityDTO activity, ConstraintValidatorContext cxt) {
        try {
            System.out.println("do ActivityDateValidatorConstraint");
            LocalDateTime activityDate = activityServices.convertDateTime(activity.getActivityDate());
            System.out.println("activityDate = "+activityDate);
            LocalDateTime activityEndDate = activityServices.convertDateTime(activity.getActivityEndDate());
            System.out.println("activityEndDate = "+activityEndDate);
            LocalDateTime announcementDate = activityServices.convertDateTime(activity.getAnnouncementDate());
            System.out.println("announcementDate = "+activityEndDate);
            if (announcementDate.isAfter(activityDate) || announcementDate.isAfter(activityEndDate)) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
    }
}