package com.nk2.unityDoServices.Entities;

import jakarta.persistence.*;

@Table(name = "activityFavorite", indexes = {
        @Index(name = "fk_activityFavorite_Users1_idx", columnList = "userId"),
        @Index(name = "fk_activityFavorite_Activity1_idx", columnList = "activityId")
})
@Entity
public class ActivityFavorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activityFavoriteId", nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "userId", nullable = false)
    private User userId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "activityId", nullable = false)
    private Activity activityId;

    public Activity getActivityId() {
        return activityId;
    }

    public void setActivityId(Activity activityId) {
        this.activityId = activityId;
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