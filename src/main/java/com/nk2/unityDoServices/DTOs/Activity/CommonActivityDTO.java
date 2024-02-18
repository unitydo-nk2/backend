package com.nk2.unityDoServices.DTOs.Activity;

import com.nk2.unityDoServices.Entities.Activity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommonActivityDTO {
    private Integer activityId;
    private Integer activityOwner;
    private String activityOwnerUserName;
    private String activityName;
    private Instant activityDate;
    private String locationName;

    // Constructor taking an Activity instance
    public CommonActivityDTO(Activity activity) {
        this.activityId = activity.getId();
        this.activityOwner = activity.getActivityOwner().getId();
        this.activityOwnerUserName = activity.getActivityOwner().getUsername();
        this.activityName = activity.getActivityName();
        this.activityDate = activity.getActivityDate();
        this.locationName = (activity.getLocationId() != null) ? activity.getLocationId().getLocationName() : null;
    }
}
