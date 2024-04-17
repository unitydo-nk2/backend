package com.nk2.unityDoServices.DTOs.Activity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nk2.unityDoServices.Interfaces.ActivityWithUserCountInterface;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime activityDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registerStartDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registerEndDate;
    private Integer amount;
    private Integer location;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime announcementDate;
    private String activityStatus;
    private Integer isGamification;
    private String suggestionNotes;
    private Integer categoryId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    private String activityFormat;
    private LocalDateTime activityEndDate;
    private Integer userCount;
}
