package com.nk2.unityDoServices.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nk2.unityDoServices.Enums.Role;
import com.nk2.unityDoServices.Validators.EnumValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Integer userId;
    private String username;
    private String password;
    private String name;
    private String surName;
    private String nickName;
    private String email;
    private String gender;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private String religion;
    private String telephoneNumber;
    private String address;
    private String role;
    private String emergencyPhoneNumber;
    private String profileImg;
    private String line;
    private String instagram;
    private String x;
    private Instant createTime;
    private Instant updateTime;
}
