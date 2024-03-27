package com.nk2.unityDoServices.DTOs.Activity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PopularActivityDTO {
    private Integer activityId;
    private String activityName;
    private String activityBriefDescription;
    private Instant activityDate;
    private Instant activityEndDate;
    private String imagePath;
    private Integer userCount;
}
