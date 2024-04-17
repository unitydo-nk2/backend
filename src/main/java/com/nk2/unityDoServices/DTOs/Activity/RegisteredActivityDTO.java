package com.nk2.unityDoServices.DTOs.Activity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime activityDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime activityEndDate;
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
