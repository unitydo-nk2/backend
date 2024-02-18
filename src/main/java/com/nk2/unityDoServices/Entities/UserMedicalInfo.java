package com.nk2.unityDoServices.Entities;

import jakarta.persistence.*;

@Table(name = "userMedicalInfo", indexes = {
        @Index(name = "fk_CongenitalDiseases_medicalInfomation1_idx", columnList = "medicalInfomation"),
        @Index(name = "fk_congenitalDiseases_Users1_idx", columnList = "userId")
})
@Entity
public class UserMedicalInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "congenitalDiseasesID", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User userId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "medicalInfomation", nullable = false)
    private MedicalInfomation medicalInfomation;

    public MedicalInfomation getMedicalInfomation() {
        return medicalInfomation;
    }

    public void setMedicalInfomation(MedicalInfomation medicalInfomation) {
        this.medicalInfomation = medicalInfomation;
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