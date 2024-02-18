package com.nk2.unityDoServices.DTOs.Activity;

import com.nk2.unityDoServices.Entities.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityBannerDTO {
    private Integer activityId;
    private String activityName;
    private String activityDescription;
    private Integer locationId;
    private Category categoryId;
    private Integer userCount;
}
