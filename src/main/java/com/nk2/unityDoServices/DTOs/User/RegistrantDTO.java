package com.nk2.unityDoServices.DTOs.User;

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
public class RegistrantDTO {
    private Integer userId;
    private String username;
    private String name;
    private String surName;
    private String gender;
    private String status;
    private LocalDateTime registrationDate;
    private Integer registrationId;
}
