package com.nk2.unityDoServices.DTOs.Image;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateNewImageDTO {
    private Integer activityId;
    private String label;
    private String alt;
    private String imagePath;
}
