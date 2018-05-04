package cn.kgc.quiz.display;

import java.util.List;

public class ReviewQuestionDisplay {
    private Integer finished;
    private Long id;
    private String questionText = "";
    private Double defaultMark;
    private Double grade;
    private Integer resultFlag;
    private Integer certainty;
    private String userIdea;
    private Double awardRate;
    private Double awardPoint;
    private String teacherFeedback;
    private String qTypeName = "";
    private String qTypeShortName = "";
    private String generalFeedback = "";
    private List<ReviewAnswerDisplay> answers;

    public Integer getFinished() {
        return finished;
    }

    public void setFinished(Integer finished) {
        this.finished = finished;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Double getDefaultMark() {
        return defaultMark;
    }

    public void setDefaultMark(Double defaultMark) {
        this.defaultMark = defaultMark;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public Integer getResultFlag() {
        return resultFlag;
    }

    public void setResultFlag(Integer resultFlag) {
        this.resultFlag = resultFlag;
    }

    public Integer getCertainty() {
        return certainty;
    }

    public void setCertainty(Integer certainty) {
        this.certainty = certainty;
    }

    public String getUserIdea() {
        return userIdea;
    }

    public void setUserIdea(String userIdea) {
        this.userIdea = userIdea;
    }

    public Double getAwardRate() {
        return awardRate;
    }

    public void setAwardRate(Double awardRate) {
        this.awardRate = awardRate;
    }

    public Double getAwardPoint() {
        return awardPoint;
    }

    public void setAwardPoint(Double awardPoint) {
        this.awardPoint = awardPoint;
    }

    public String getTeacherFeedback() {
        return teacherFeedback;
    }

    public void setTeacherFeedback(String teacherFeedback) {
        this.teacherFeedback = teacherFeedback;
    }

    public String getqTypeName() {
        return qTypeName;
    }

    public void setqTypeName(String qTypeName) {
        this.qTypeName = qTypeName;
    }

    public String getqTypeShortName() {
        return qTypeShortName;
    }

    public void setqTypeShortName(String qTypeShortName) {
        this.qTypeShortName = qTypeShortName;
    }

    public String getGeneralFeedback() {
        return generalFeedback;
    }

    public void setGeneralFeedback(String generalFeedback) {
        this.generalFeedback = generalFeedback;
    }

    public List<ReviewAnswerDisplay> getAnswers() {
        return answers;
    }

    public void setAnswers(List<ReviewAnswerDisplay> answers) {
        this.answers = answers;
    }
}
