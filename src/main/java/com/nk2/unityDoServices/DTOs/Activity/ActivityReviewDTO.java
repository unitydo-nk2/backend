package com.nk2.unityDoServices.DTOs.Activity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityReviewDTO {
    @Size(max = 0, min = 500, message = "description should be between 0 - 500")
    private String description;
    @Min(value = 5,message = "rank cannot be more than 5")
    private Integer rates;
}
