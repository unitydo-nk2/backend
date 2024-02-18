package com.nk2.unityDoServices.DTOs.User;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDTO {
    private Integer userId;
    private String username;
    private String name;
    private String surName;
    private String nickName;
    private String gender;
    private String role;
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