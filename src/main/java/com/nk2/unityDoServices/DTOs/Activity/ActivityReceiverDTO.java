package com.nk2.unityDoServices.DTOs.Activity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityReceiverDTO {
    private int activityId;
    private String activityName;
    private String mainCategory;
    private double distance;
}
