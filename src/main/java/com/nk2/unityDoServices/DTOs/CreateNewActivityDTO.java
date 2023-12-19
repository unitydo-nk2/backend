package com.nk2.unityDoServices.DTOs;

import com.nk2.unityDoServices.Enums.ActivityFormat;
import com.nk2.unityDoServices.Enums.ActivityStatus;
import com.nk2.unityDoServices.Validators.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ActivityDateValidator
@RegistrationDateValidator
@AnnouncementDateValidator
@RegistrationEndDateValidator
public class CreateNewActivityDTO {
    @NotBlank(message = "activity name cannot be blank")
    @NotNull(message = "activity name cannot be null")
    @Size(max = 50, message = "activity name cannot be longer than 50 characters")
    private String activityName;
    @Size(max = 100, message = "activity brief description cannot be longer than 200 characters")
    private String activityBriefDescription;
    @NotNull(message = "activity description cannot be null")
    @Size(max = 300, message = "activity description cannot be longer than 400 characters")
    private String activityDescription;
    @NotNull(message = "activity date cannot be null")
    private String activityDate;
    @NotNull(message = "register (start)date cannot be null")
    private String registerStartDate;
    @NotNull(message = "register (end)date cannot be null")
    private String registerEndDate;
    private Integer amount;
    @NotNull(message = "announcement date cannot be null")
    private String announcementDate;
    @EnumValidator(enumClass = ActivityStatus.class)
    private String activityStatus;
    @NotNull(message = "Gamification cannot be null")
    private Integer isGamification;
    @Size(max = 500, message = "suggestion notes cannot be longer than 500 characters")
    private String suggestionNotes;
    @NotNull(message = "category ID cannot be null")
    private Integer categoryId;
    @EnumValidator(enumClass = ActivityFormat.class)
    @NotNull(message = "activity format cannot be null")
    private String activityFormat;
    private String activityEndDate;
}

