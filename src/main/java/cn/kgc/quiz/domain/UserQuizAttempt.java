package cn.kgc.quiz.domain;

import java.util.Date;


public class UserQuizAttempt implements java.io.Serializable {

    private Long id;
    private Quiz quiz;
    private Long quizId;
    private QuizUser sysUser;
    private Long quizUserId;
    private Integer quizAttemptSeq;
    private Integer previewTimes;
    private Integer state;
    private Date timeStart;
    private Date timeFinish;
    private Date timeModified;
    private Double sumGrades;
    private String gradeFeedback;
    private Integer currentExamType;
    private Integer currentFeedbackType;
    private Integer attemptsRemained;
    private String uuidFlag;

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

    public Integer getQuizAttemptSeq() {
        return quizAttemptSeq;
    }

    public void setQuizAttemptSeq(Integer quizAttemptSeq) {
        this.quizAttemptSeq = quizAttemptSeq;
    }

    public Integer getPreviewTimes() {
        return previewTimes;
    }

    public void setPreviewTimes(Integer previewTimes) {
        this.previewTimes = previewTimes;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    public Date getTimeFinish() {
        return timeFinish;
    }

    public void setTimeFinish(Date timeFinish) {
        this.timeFinish = timeFinish;
    }

    public Date getTimeModified() {
        return timeModified;
    }

    public void setTimeModified(Date timeModified) {
        this.timeModified = timeModified;
    }

    public Double getSumGrades() {
        return sumGrades;
    }

    public void setSumGrades(Double sumGrades) {
        this.sumGrades = sumGrades;
    }

    public String getGradeFeedback() {
        return gradeFeedback;
    }

    public void setGradeFeedback(String gradeFeedback) {
        this.gradeFeedback = gradeFeedback;
    }

    public Integer getCurrentExamType() {
        return currentExamType;
    }

    public void setCurrentExamType(Integer currentExamType) {
        this.currentExamType = currentExamType;
    }

    public Integer getCurrentFeedbackType() {
        return currentFeedbackType;
    }

    public void setCurrentFeedbackType(Integer currentFeedbackType) {
        this.currentFeedbackType = currentFeedbackType;
    }

    public Integer getAttemptsRemained() {
        return attemptsRemained;
    }

    public void setAttemptsRemained(Integer attemptsRemained) {
        this.attemptsRemained = attemptsRemained;
    }

    public String getUuidFlag() {
        return uuidFlag;
    }

    public void setUuidFlag(String uuidFlag) {
        this.uuidFlag = uuidFlag;
    }
}