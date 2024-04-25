package com.nk2.unityDoServices.DTOs.User;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registrationDate;
    private Integer registrationId;
}
