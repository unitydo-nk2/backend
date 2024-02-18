package com.nk2.unityDoServices.Entities;

import jakarta.persistence.*;

@Table(name = "answer", indexes = {
        @Index(name = "fk_Answer_QuestionTitle1_idx", columnList = "questionId"),
        @Index(name = "fk_answer_Registration1_idx", columnList = "registrationId")
})
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answerId", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "registrationId")
    private Registration registrationId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "questionId", nullable = false)
    private QuestionTitle questionId;

    @Column(name = "answer", nullable = false, length = 400)
    private String answer;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public QuestionTitle getQuestionId() {
        return questionId;
    }

    public void setQuestionId(QuestionTitle questionId) {
        this.questionId = questionId;
    }

    public Registration getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(Registration registrationId) {
        this.registrationId = registrationId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}