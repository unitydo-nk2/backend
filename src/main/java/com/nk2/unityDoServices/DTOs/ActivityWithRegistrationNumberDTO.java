package com.nk2.unityDoServices.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityWithRegistrationNumberDTO {
    private Integer activityId;
    private Integer activityOwnerId;
    private String activityName;
    private Instant activityDate;
    private Integer amount;
    private Instant activityEndDate;
    private Integer userRegistration;
}
