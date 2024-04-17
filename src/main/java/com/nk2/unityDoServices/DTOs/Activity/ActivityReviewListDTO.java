package com.nk2.unityDoServices.DTOs.Activity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nk2.unityDoServices.Entities.ActivityReview;
import com.nk2.unityDoServices.Entities.User;
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
public class ActivityReviewListDTO {
    private String name;
    private String surName;
    private String description;
    private Integer rates;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    public ActivityReviewListDTO(User user, ActivityReview activityReview){
        this.name = user.getName();
        this.surName = user.getSurName();
        this.description = activityReview.getDescription();
        this.rates = activityReview.getRates();
        this.createTime = activityReview.getCreateTime();
    }
}
