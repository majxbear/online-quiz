package cn.kgc.quiz.domain;

import java.util.Date;


public class UserQuizExpected implements java.io.Serializable {

    private Long id;
    private Quiz quiz;
    private Long quizId;
    private QuizUser sysUser;
    private Long quizUserId;
    private Date timeOpen;
    private Date timeClose;
    private Integer attemptsAllowed;
    private Integer minutesAllowed;
    private Integer currentFeedbackType;
    private Integer examType;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public QuizUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(QuizUser sysUser) {
        this.sysUser = sysUser;
    }

    public Long getQuizUserId() {
        return quizUserId;
    }

    public void setQuizUserId(Long quizUserId) {
        this.quizUserId = quizUserId;
    }

    public Date getTimeOpen() {
        return timeOpen;
    }

    public void setTimeOpen(Date timeOpen) {
        this.timeOpen = timeOpen;
    }

    public Date getTimeClose() {
        return timeClose;
    }

    public void setTimeClose(Date timeClose) {
        this.timeClose = timeClose;
    }

    public Integer getAttemptsAllowed() {
        return attemptsAllowed;
    }

    public void setAttemptsAllowed(Integer attemptsAllowed) {
        this.attemptsAllowed = attemptsAllowed;
    }

    public Integer getMinutesAllowed() {
        return minutesAllowed;
    }

    public void setMinutesAllowed(Integer minutesAllowed) {
        this.minutesAllowed = minutesAllowed;
    }

    public Integer getCurrentFeedbackType() {
        return currentFeedbackType;
    }

    public void setCurrentFeedbackType(Integer currentFeedbackType) {
        this.currentFeedbackType = currentFeedbackType;
    }

    public Integer getExamType() {
        return examType;
    }

    public void setExamType(Integer examType) {
        this.examType = examType;
    }
}