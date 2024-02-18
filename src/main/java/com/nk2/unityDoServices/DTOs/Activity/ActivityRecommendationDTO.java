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
public class ActivityRecommendationDTO {
    private Integer activityId;
    private String activityName;
    private Integer categoryId;
    private String category;
    private String mainCategory;
    private String activityBriefDescription;
    private Instant activityDate;
    private String locationId;
    private String activityFormat;
    private Instant activityEndDate;

    public ActivityRecommendationDTO(Activity activity) {
        this.activityId = activity.getId();
        this.activityName = activity.getActivityName();
        this.categoryId = activity.getCategoryId().getId();
        this.category = activity.getCategoryId().getCategory();
        this.mainCategory = activity.getCategoryId().getMainCategory().getMainCategory();
        this.activityBriefDescription = activity.getActivityBriefDescription();
        this.activityDate = activity.getActivityEndDate();
        if(activity.getLocationId() == null){
            this.locationId = null;
        }else{
            this.locationId = activity.getLocationId().getLocationName();
        }
        this.activityFormat = activity.getActivityFormat();
        this.activityEndDate = activity.getActivityEndDate();
    }
}
