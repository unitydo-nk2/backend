package com.nk2.unityDoServices.DTOs;

import com.nk2.unityDoServices.Entities.Category;
import com.nk2.unityDoServices.Entities.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

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
