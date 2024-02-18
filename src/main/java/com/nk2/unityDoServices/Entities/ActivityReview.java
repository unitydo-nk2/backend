package com.nk2.unityDoServices.Entities;

import jakarta.persistence.*;

@Table(name = "activityReview", indexes = {
        @Index(name = "fk_activityReview_Users1_idx", columnList = "userId"),
        @Index(name = "fk_activityReview_Activity1_idx", columnList = "activityId")
})
@Entity
public class ActivityReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activityReviewId", nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "activityId", nullable = false)
    private Activity activityId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "userId", nullable = false)
    private User userId;

    @Column(name = "score", nullable = false)
    private Integer score;

    @Column(name = "reviewDescription", nullable = false, length = 500)
    private String reviewDescription;

    public String getReviewDescription() {
        return reviewDescription;
    }

    public void setReviewDescription(String reviewDescription) {
        this.reviewDescription = reviewDescription;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
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