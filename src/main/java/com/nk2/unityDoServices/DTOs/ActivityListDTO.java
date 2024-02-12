package com.nk2.unityDoServices.DTOs;

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
public class ActivityListDTO {
    private Integer id;
    private Integer activityOwnerId;
    private String activityOwnerUsername;
    private String activityName;
    private Integer categoryId;
    private String category;
    private Integer mainCategoryId;
    private String mainCategory;
    private Instant activityDate;
    private Instant activityEndDate;
    private String imagePath;

    public ActivityListDTO(Activity activity) {
        this.id = activity.getId();
        this.activityOwnerId = activity.getActivityOwner().getId();
        this.activityOwnerUsername = activity.getActivityOwner().getUsername();
        this.activityName = activity.getActivityName();
        this.categoryId = activity.getCategoryId().getId();
        this.category = activity.getCategoryId().getCategory();
        this.mainCategoryId = activity.getCategoryId().getMainCategory().getId();
        this.mainCategory = activity.getCategoryId().getMainCategory().getMainCategory();
        this.activityDate = activity.getActivityDate();
        this.activityEndDate = activity.getActivityEndDate();

    }
}
