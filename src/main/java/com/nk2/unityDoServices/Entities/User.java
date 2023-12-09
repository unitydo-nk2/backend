package com.nk2.unityDoServices.Entities;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDate;

@Table(name = "user")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", nullable = false)
    private Integer id;

    @Column(name = "username", nullable = false, length = 100)
    private String username;

    @Column(name = "password", nullable = false, length = 250)
    private String password;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "surName", nullable = false, length = 100)
    private String surName;

    @Column(name = "nickName", nullable = false, length = 70)
    private String nickName;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "gender", nullable = false, length = 50)
    private String gender;

    @Column(name = "dateOfBirth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "religion", nullable = false, length = 50)
    private String religion;

    @Column(name = "telephoneNumber", nullable = false, length = 50)
    private String telephoneNumber;

    @Column(name = "address", nullable = false, length = 400)
    private String address;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "emergencyPhoneNumber", nullable = false, length = 45)
    private String emergencyPhoneNumber;

    @Column(name = "profileImg", length = 300)
    private String profileImg;

    @Column(name = "line", length = 100)
    private String line;

    @Column(name = "instagram", length = 100)
    private String instagram;

    @Column(name = "x", length = 100)
    private String x;

    @Column(name = "createTime", nullable = false)
    private Instant createTime;

    @Column(name = "updateTime", nullable = false)
    private Instant updateTime;

    public Instant getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getEmergencyPhoneNumber() {
        return emergencyPhoneNumber;
    }

    public void setEmergencyPhoneNumber(String emergencyPhoneNumber) {
        this.emergencyPhoneNumber = emergencyPhoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}