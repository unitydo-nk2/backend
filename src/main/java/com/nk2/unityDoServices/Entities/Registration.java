package com.nk2.unityDoServices.Entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Table(name = "registration", indexes = {
        @Index(name = "fk_Registration_Activity1_idx", columnList = "activityId"),
        @Index(name = "fk_Registration_Users1_idx", columnList = "userId")
}, uniqueConstraints = {
        @UniqueConstraint(name = "registrationId_UNIQUE", columnNames = {"registrationId"})
})
@Entity
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "registrationId", nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "userId", nullable = false)
    private User userId;

    @Column(name = "registrationDate", nullable = false)
    private LocalDateTime registrationDate;

    @Lob
    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "activityId", nullable = false)
    private Activity activityId;

    public Activity getActivityId() {
        return activityId;
    }

    public void setActivityId(Activity activityId) {
        this.activityId = activityId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}