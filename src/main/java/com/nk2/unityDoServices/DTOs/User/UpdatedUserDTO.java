package com.nk2.unityDoServices.DTOs.User;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatedUserDTO {
    private String name;
    private String surName;
    private String nickName;
    private String gender;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private String religion;
    private String telephoneNumber;
    private String address;
    private String emergencyPhoneNumber;
    private String profileImg;
    private String line;
    private String instagram;
    private String x;
}
