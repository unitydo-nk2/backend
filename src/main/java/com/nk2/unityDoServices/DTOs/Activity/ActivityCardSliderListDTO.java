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
public class ActivityCardSliderListDTO {
    private Integer activityId;
    private String activityName;
    private String activityBriefDescription;
    private String category;
    private String mainCategory;
    private String location;
    private String activityDescription;
    private Instant activityDate;
    private String imagePath;
}
