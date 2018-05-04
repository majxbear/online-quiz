package cn.kgc.quiz.display;

import java.util.List;

public class ReviewQuizDisplay {
    private Long quizId;
    private Long attemptId;
    private Long userId;
    private String quizName = "";
    private String intro = "";
    private Integer examType;
    private Integer feedbackType;
    private Integer minutesAllowed;
    private Double userSumGrades;
    private Double quizSumGrades;
    private String gradeFeedback;
    private List<ReviewQuestionDisplay> questions;

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public Long getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(Long attemptId) {
        this.attemptId = attemptId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Integer getExamType() {
        return examType;
    }

    public void setExamType(Integer examType) {
        this.examType = examType;
    }

    public Integer getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(Integer feedbackType) {
        this.feedbackType = feedbackType;
    }

    public Integer getMinutesAllowed() {
        return minutesAllowed;
    }

    public void setMinutesAllowed(Integer minutesAllowed) {
        this.minutesAllowed = minutesAllowed;
    }

    public Double getUserSumGrades() {
        return userSumGrades;
    }

    public void setUserSumGrades(Double userSumGrades) {
        this.userSumGrades = userSumGrades;
    }

    public Double getQuizSumGrades() {
        return quizSumGrades;
    }

    public void setQuizSumGrades(Double quizSumGrades) {
        this.quizSumGrades = quizSumGrades;
    }

    public String getGradeFeedback() {
        return gradeFeedback;
    }

    public void setGradeFeedback(String gradeFeedback) {
        this.gradeFeedback = gradeFeedback;
    }

    public List<ReviewQuestionDisplay> getQuestions() {
        return questions;
    }

    public void setQuestions(List<ReviewQuestionDisplay> questions) {
        this.questions = questions;
    }
}
