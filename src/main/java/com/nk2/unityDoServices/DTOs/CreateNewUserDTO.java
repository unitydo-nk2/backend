package com.nk2.unityDoServices.DTOs;

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
public class CreateNewUserDTO {
    private String username;
    @NotBlank(message = "password is required")
    @Size(max = 14, min = 8, message = "password name must be between 8-14 characters")
    private String password;
    @Max(value = 50, message = "name cannot be more than 50 alphabet")
    private String name;
    @Max(value = 50, message = "surname cannot be more than 50 alphabet")
    private String surName;
    @Max(value = 50, message = "nickname cannot be more than 50 alphabet")
    private String nickName;
    @Email(message = "Email should be in email format")
    private String email;
    @NotBlank(message = "gender is required")
    private String gender;
    @NotBlank(message = "date of birth is required")
    private LocalDate dateOfBirth;
    @NotBlank(message = "religion is required")
    private String religion;
    @NotBlank(message = "telephone number is required")
    private String telephoneNumber;
    @NotBlank(message = "address is required")
    private String address;
    @NotBlank(message = "role is required")
    @EnumValidator(enumClass = Role.class)
    private String role;
    @NotBlank(message = "emergency phone number is required")
    private String emergencyPhoneNumber;
    private String profileImg;
    private String line;
    private String instagram;
    private String x;
    @NotBlank(message = "createTime is required")
    private Instant createTime;
    @NotBlank(message = "updateTime is required")
    private Instant updateTime;
}

