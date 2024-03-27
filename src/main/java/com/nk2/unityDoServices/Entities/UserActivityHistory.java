package com.nk2.unityDoServices.Entities;

import jakarta.persistence.*;

@Table(name = "userActivityHistory", indexes = {
        @Index(name = "fk_activityHistory_Activity1_idx", columnList = "activityId"),
        @Index(name = "fk_activityHistory_Users1_idx", columnList = "userId")
})
@Entity
public class UserActivityHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activityHistoryId", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "userId")
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