package com.nk2.unityDoServices.DTOs.Activity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityImageDTO {
    private Integer imageId;
    private Integer activityId;
    private String  label;
    private String  alt;
    private String  imagePath;
}