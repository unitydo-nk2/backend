package com.nk2.unityDoServices.Entities;

import jakarta.persistence.*;

@Table(name = "requirements", indexes = {
        @Index(name = "fk_requirements_Activity1_idx", columnList = "activityId"),
        @Index(name = "fk_requirements_medicalInfomation1_idx", columnList = "medicalInfomation")
})
@Entity
public class Requirement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "requirementId", nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "activityId", nullable = false)
    private Activity activityId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "medicalInfomation", nullable = false)
    private Medicalinfomation medicalInfomation;

    public Medicalinfomation getMedicalInfomation() {
        return medicalInfomation;
    }

    public void setMedicalInfomation(Medicalinfomation medicalInfomation) {
        this.medicalInfomation = medicalInfomation;
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