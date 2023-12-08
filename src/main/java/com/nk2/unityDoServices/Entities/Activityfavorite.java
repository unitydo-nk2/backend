package com.nk2.unityDoServices.Entities;

import jakarta.persistence.*;

@Table(name = "activityfavorite", indexes = {
        @Index(name = "fk_activityFavorite_ActivityHistory1_idx", columnList = "activityHistoryId")
})
@Entity
public class Activityfavorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activityFavoriteID", nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "activityHistoryId", nullable = false)
    private Useractivityhistory activityHistoryId;

    @Column(name = "isFavorite", nullable = false)
    private Integer isFavorite;

    public Integer getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(Integer isFavorite) {
        this.isFavorite = isFavorite;
    }

    public Useractivityhistory getActivityHistoryId() {
        return activityHistoryId;
    }

    public void setActivityHistoryId(Useractivityhistory activityHistoryId) {
        this.activityHistoryId = activityHistoryId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}