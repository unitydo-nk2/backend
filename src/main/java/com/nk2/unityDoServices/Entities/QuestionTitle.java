package com.nk2.unityDoServices.Entities;

import jakarta.persistence.*;

@Table(name = "questionTitle", indexes = {
        @Index(name = "fk_questionTitle_Activity1_idx", columnList = "activityId")
})
@Entity
public class QuestionTitle {
    @Id
    @Column(name = "questionId", nullable = false)
    private Integer id;

    @Column(name = "Question", nullable = false, length = 100)
    private String question;

    @ManyToOne
    @JoinColumn(name = "activityId")
    private Activity activityId;

    public Activity getActivityId() {
        return activityId;
    }

    public void setActivityId(Activity activityId) {
        this.activityId = activityId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}