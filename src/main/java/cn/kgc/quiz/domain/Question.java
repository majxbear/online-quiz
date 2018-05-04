package cn.kgc.quiz.domain;

import java.util.ArrayList;
import java.util.List;


public class Question implements java.io.Serializable {
    private Long id;
    private String name;
    private String questionText;
    private String keywords;
    private Integer questionType;
    private String generalFeedback;
    private Double defaultMark;
    private Long courseId;
    private String uuidFlag;
    private Integer attemptsAllowed;
    private Double attemptPenalty;
    private List<QuestionAnswer> questionAnswers = new ArrayList<QuestionAnswer>(0);
    private List<Hint> hints = new ArrayList<Hint>(0);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public String getGeneralFeedback() {
        return generalFeedback;
    }

    public void setGeneralFeedback(String generalFeedback) {
        this.generalFeedback = generalFeedback;
    }

    public Double getDefaultMark() {
        return defaultMark;
    }

    public void setDefaultMark(Double defaultMark) {
        this.defaultMark = defaultMark;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getUuidFlag() {
        return uuidFlag;
    }

    public void setUuidFlag(String uuidFlag) {
        this.uuidFlag = uuidFlag;
    }

    public Integer getAttemptsAllowed() {
        return attemptsAllowed;
    }

    public void setAttemptsAllowed(Integer attemptsAllowed) {
        this.attemptsAllowed = attemptsAllowed;
    }

    public Double getAttemptPenalty() {
        return attemptPenalty;
    }

    public void setAttemptPenalty(Double attemptPenalty) {
        this.attemptPenalty = attemptPenalty;
    }

    public List<QuestionAnswer> getQuestionAnswers() {
        return questionAnswers;
    }

    public void setQuestionAnswers(List<QuestionAnswer> questionAnswers) {
        this.questionAnswers = questionAnswers;
    }

    public List<Hint> getHints() {
        return hints;
    }

    public void setHints(List<Hint> hints) {
        this.hints = hints;
    }
}