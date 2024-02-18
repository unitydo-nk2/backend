package com.nk2.unityDoServices.DTOs.User;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrantDetailsDTO {
    private Integer userId;
    private String username;
    private String name;
    private String surName;
    private String nickName;
    private String gender;
    private String religion;
    private String email;
    private String telephoneNumber;
    private String emergencyPhoneNumber;
    private String address;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private Integer registrationId;
    private String status;
}
