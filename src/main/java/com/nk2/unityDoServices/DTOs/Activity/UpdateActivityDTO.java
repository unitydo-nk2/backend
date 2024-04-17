package com.nk2.unityDoServices.DTOs.Activity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateActivityDTO {
    private Integer activityId;
    private String activityName;
    private String activityBriefDescription;
    private String activityDescription;
    private String activityDate;
    private String registerStartDate;
    private String registerEndDate;
    private Integer amount;
    private Integer locationId;
    private String locationName;
    private String googleMapLink;
    private String announcementDate;
    private String activityStatus;
    private String suggestionNotes;
    private Integer categoryId;
    private String category;
    private String mainCategory;
    private String lastUpdate;
    private String activityFormat;
    private String activityEndDate;
}
