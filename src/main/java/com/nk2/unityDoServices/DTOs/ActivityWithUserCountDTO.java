package com.nk2.unityDoServices.DTOs;

import com.nk2.unityDoServices.Interfaces.ActivityWithUserCountInterface;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityWithUserCountDTO {
    private Integer activityId;
    private Integer activityOwner;
    private String activityName;
    private String activityBriefDescription;
    private String activityDescription;
    private Instant activityDate;
    private Instant registerStartDate;
    private Instant registerEndDate;
    private Integer amount;
    private Integer location;
    private Instant announcementDate;
    private String activityStatus;
    private Integer isGamification;
    private String suggestionNotes;
    private Integer categoryId;
    private Instant lastUpdate;
    private Instant createTime;
    private String activityFormat;
    private Instant activityEndDate;
    private Integer userCount;
}
