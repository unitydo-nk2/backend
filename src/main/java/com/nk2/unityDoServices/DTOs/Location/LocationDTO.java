package com.nk2.unityDoServices.DTOs.Location;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {
    @NotNull(message = "location name can not be null")
    @Size(max = 300,message = "location name can not more than 300 characters")
    private String locationName;
    @NotNull(message = "link can not be null")
    private String googleMapLink;
    private Double locationLongitude;
    private Double locationLatitude;
}
