package com.nk2.unityDoServices.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Future;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityRegisterDTO {
    private Integer userId;
    private Integer activityId;
    @Future(message = "registration should be future date time")
    private Instant registrationDate;
}
