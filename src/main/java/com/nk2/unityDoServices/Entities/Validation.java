package com.nk2.unityDoServices.Entities;

import jakarta.persistence.*;

@Table(name = "validation", indexes = {
        @Index(name = "fk_Validation_Activity1_idx", columnList = "activityId")
})
@Entity
public class Validation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "validationId", nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "activityId", nullable = false)
    private Activity activityId;

    @Column(name = "validationType", nullable = false, length = 100)
    private String validationType;

    @Column(name = "validationRule", nullable = false)
    private Double validationRule;

    public Double getValidationRule() {
        return validationRule;
    }

    public void setValidationRule(Double validationRule) {
        this.validationRule = validationRule;
    }

    public String getValidationType() {
        return validationType;
    }

    public void setValidationType(String validationType) {
        this.validationType = validationType;
    }

    public Activity getActivityId() {
        return activityId;
    }

    public void setActivityId(Activity activityId) {
        this.activityId = activityId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}