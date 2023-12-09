package com.nk2.unityDoServices.Entities;

import jakarta.persistence.*;

@Table(name = "usermedicalinfo", indexes = {
        @Index(name = "fk_CongenitalDiseases_medicalInfomation1_idx", columnList = "medicalInfomation"),
        @Index(name = "fk_congenitalDiseases_Users1_idx", columnList = "userId")
})
@Entity
public class Usermedicalinfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "congenitalDiseasesID", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User userId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "medicalInfomation", nullable = false)
    private Medicalinfomation medicalInfomation;

    public Medicalinfomation getMedicalInfomation() {
        return medicalInfomation;
    }

    public void setMedicalInfomation(Medicalinfomation medicalInfomation) {
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