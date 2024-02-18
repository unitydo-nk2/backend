package com.nk2.unityDoServices.DTOs.User;

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
public class UserRegistrationDTO {
    private String name;
    private String surName;
    private String username;
    private String password;
    private String nickName;
    private String email;
    private String gender;
    private LocalDate dateOfBirth;
    private String religion;
    private String telephoneNumber;
    private String address;
    private String emergencyPhoneNumber;
}
