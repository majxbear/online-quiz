package cn.kgc.quiz.display;

import java.util.List;

public class QuizDisplay {
    private Long quizId;
    private Long attemptId;
    private Long userId;
    private String quizName = "";
    private String intro = "";
    private Double quizSumGrades;
    private Integer minutesAllowed;
    private Integer examType;
    private Integer feedbackType;
    private List<QuestionDisplay> questions;

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

    public Double getQuizSumGrades() {
        return quizSumGrades;
    }

    public Integer getMinutesAllowed() {
        return minutesAllowed;
    }

    public void setMinutesAllowed(Integer minutesAllowed) {
        this.minutesAllowed = minutesAllowed;
    }

    public void setQuizSumGrades(Double quizSumGrades) {
        this.quizSumGrades = quizSumGrades;
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

    public List<QuestionDisplay> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDisplay> questions) {
        this.questions = questions;
    }
}
