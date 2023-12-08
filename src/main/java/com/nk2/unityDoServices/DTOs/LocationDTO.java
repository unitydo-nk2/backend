package com.nk2.unityDoServices.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {
    private String locationName;
    private String googleMapLink;
    private Double locationLongitude;
    private Double locationLatitude;
}
