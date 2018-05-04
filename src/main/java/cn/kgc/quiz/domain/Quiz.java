package cn.kgc.quiz.domain;

import java.util.Date;


public class Quiz implements java.io.Serializable {

    private Long id;
    private Long courseId;
    private String name;
    private String intro;
    private Date timeOpen;
    private Date timeClose;
    private Integer minutesAllowed;
    private Integer attemptsAllowed;
    private Integer gradeMethod;
    private Integer examType;
    private Integer feedbackType;
    private Integer reviewAttempt;
    private Integer reviewCorrectness;
    private Integer reviewMarks;
    private Integer reviewSpecialFeedback;
    private Integer reviewGeneralFeedback;
    private Integer reviewRightAnswer;
    private Integer reviewScoreFeedback;
    private Double sumGrades;
    private String lev1FeedbackText;
    private String lev2FeedbackText;
    private String lev3FeedbackText;
    private String lev4FeedbackText;
    private String lev5FeedbackText;
    private String uuidFlag;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
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

    public Integer getGradeMethod() {
        return gradeMethod;
    }

    public void setGradeMethod(Integer gradeMethod) {
        this.gradeMethod = gradeMethod;
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

    public Integer getReviewAttempt() {
        return reviewAttempt;
    }

    public void setReviewAttempt(Integer reviewAttempt) {
        this.reviewAttempt = reviewAttempt;
    }

    public Integer getReviewCorrectness() {
        return reviewCorrectness;
    }

    public void setReviewCorrectness(Integer reviewCorrectness) {
        this.reviewCorrectness = reviewCorrectness;
    }

    public Integer getReviewMarks() {
        return reviewMarks;
    }

    public void setReviewMarks(Integer reviewMarks) {
        this.reviewMarks = reviewMarks;
    }

    public Integer getReviewSpecialFeedback() {
        return reviewSpecialFeedback;
    }

    public void setReviewSpecialFeedback(Integer reviewSpecialFeedback) {
        this.reviewSpecialFeedback = reviewSpecialFeedback;
    }

    public Integer getReviewGeneralFeedback() {
        return reviewGeneralFeedback;
    }

    public void setReviewGeneralFeedback(Integer reviewGeneralFeedback) {
        this.reviewGeneralFeedback = reviewGeneralFeedback;
    }

    public Integer getReviewRightAnswer() {
        return reviewRightAnswer;
    }

    public void setReviewRightAnswer(Integer reviewRightAnswer) {
        this.reviewRightAnswer = reviewRightAnswer;
    }

    public Integer getReviewScoreFeedback() {
        return reviewScoreFeedback;
    }

    public void setReviewScoreFeedback(Integer reviewScoreFeedback) {
        this.reviewScoreFeedback = reviewScoreFeedback;
    }

    public Double getSumGrades() {
        return sumGrades;
    }

    public void setSumGrades(Double sumGrades) {
        this.sumGrades = sumGrades;
    }

    public String getLev1FeedbackText() {
        return lev1FeedbackText;
    }

    public void setLev1FeedbackText(String lev1FeedbackText) {
        this.lev1FeedbackText = lev1FeedbackText;
    }

    public String getLev2FeedbackText() {
        return lev2FeedbackText;
    }

    public void setLev2FeedbackText(String lev2FeedbackText) {
        this.lev2FeedbackText = lev2FeedbackText;
    }

    public String getLev3FeedbackText() {
        return lev3FeedbackText;
    }

    public void setLev3FeedbackText(String lev3FeedbackText) {
        this.lev3FeedbackText = lev3FeedbackText;
    }

    public String getLev4FeedbackText() {
        return lev4FeedbackText;
    }

    public void setLev4FeedbackText(String lev4FeedbackText) {
        this.lev4FeedbackText = lev4FeedbackText;
    }

    public String getLev5FeedbackText() {
        return lev5FeedbackText;
    }

    public void setLev5FeedbackText(String lev5FeedbackText) {
        this.lev5FeedbackText = lev5FeedbackText;
    }

    public String getUuidFlag() {
        return uuidFlag;
    }

    public void setUuidFlag(String uuidFlag) {
        this.uuidFlag = uuidFlag;
    }
}