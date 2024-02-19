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
public class RegisteredActivityDTO {
    private Integer id;
    private Integer activityOwner;
    private String activityName;
    private Integer categoryId;
    private Integer mainCategoryId;
    private Instant activityDate;
    private Instant activityEndDate;
    private String imagePath;
    private String status;

    public RegisteredActivityDTO(ActivityWithStatusDTO activity) {
        this.id = activity.getActivityId();
        this.activityOwner = activity.getActivityOwner();
        this.activityName = activity.getActivityName();
        this.categoryId = activity.getCategoryId();
        this.activityDate = activity.getActivityDate();
        this.activityEndDate = activity.getActivityEndDate();
        this.imagePath = null;
        this.status = activity.getStatus();
    }
}
