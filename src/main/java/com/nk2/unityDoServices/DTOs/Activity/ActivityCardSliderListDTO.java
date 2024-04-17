package com.nk2.unityDoServices.DTOs.Activity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nk2.unityDoServices.Entities.Activity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime activityDate;
    private String imagePath;

    public ActivityCardSliderListDTO(Activity activity){
        this.activityId = activity.getId();
        this.activityName = activity.getActivityName();
        this.activityBriefDescription = activity.getActivityBriefDescription();
        this.category = activity.getCategoryId().getCategory();
        this.mainCategory = activity.getCategoryId().getMainCategory().getMainCategory();
        this.location = activity.getLocationId().getLocationName();
        this.activityDescription = activity.getActivityDescription();
        this.activityDate = activity.getActivityDate();
    }

}
