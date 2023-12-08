package com.nk2.unityDoServices.Entities;

import jakarta.persistence.*;

@Table(name = "answer", indexes = {
        @Index(name = "fk_answer_Registeration1_idx", columnList = "registrationId"),
        @Index(name = "fk_Answer_QuestionTitle1_idx", columnList = "questionId")
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
    private Questiontitle questionId;

    @Column(name = "answer", nullable = false, length = 400)
    private String answer;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Questiontitle getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Questiontitle questionId) {
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