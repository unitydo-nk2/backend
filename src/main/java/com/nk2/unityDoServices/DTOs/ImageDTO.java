package com.nk2.unityDoServices.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageDTO {
    private Integer id;
    private Integer activityId;
    private String label;
    private String alt;
    private String imagepath;
}
