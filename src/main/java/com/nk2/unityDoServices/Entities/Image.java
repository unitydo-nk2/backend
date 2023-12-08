package com.nk2.unityDoServices.Entities;

import jakarta.persistence.*;

@Table(name = "image", indexes = {
        @Index(name = "fk_image_Activity1_idx", columnList = "activityId")
})
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "imageId", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "activityId")
    private Activity activityId;

    @Column(name = "label", nullable = false, length = 100)
    private String label;

    @Column(name = "alt", nullable = false, length = 100)
    private String alt;

    @Column(name = "imagepath", nullable = false, length = 300)
    private String imagepath;

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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