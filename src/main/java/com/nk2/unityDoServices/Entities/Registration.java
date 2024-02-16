package com.nk2.unityDoServices.Entities;

import com.nk2.unityDoServices.Enums.RegistrationStatus;
import com.nk2.unityDoServices.Validators.Validators.EnumValidator;
import jakarta.persistence.*;

import java.time.Instant;

@Table(name = "registration", indexes = {
        @Index(name = "fk_Registration_Activity1_idx", columnList = "activityId"),
        @Index(name = "fk_Registration_Users1_idx", columnList = "userId")
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
    private Instant registrationDate;

    @EnumValidator(enumClass = RegistrationStatus.class)
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

    public Instant getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Instant registrationDate) {
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