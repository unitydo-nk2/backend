package com.nk2.unityDoServices.Entities;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Table(name = "activityReview", indexes = {
        @Index(name = "fk_activityReview_Registration1_idx", columnList = "registrationId")
})
@Entity
public class ActivityReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activityReviewId", nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "registrationId", nullable = false)
    private Registration registrationId;

    @Column(name = "rates", nullable = false)
    private Integer rates;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "createTime", nullable = false)
    private LocalDateTime createTime;

    @Column(name = "updateTime", nullable = false)
    private LocalDateTime updateTime;

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRates() {
        return rates;
    }

    public void setRates(Integer rates) {
        this.rates = rates;
    }

    public Registration getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(Registration registrationId) {
        this.registrationId = registrationId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}