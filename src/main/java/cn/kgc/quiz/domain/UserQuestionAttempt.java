package cn.kgc.quiz.domain;


import java.util.Date;

public class UserQuestionAttempt implements java.io.Serializable {
    private Long id;
    private Question question;
    private Long questionId;
    private UserQuizAttempt userQuizAttempt;
    private Long userQuizAttemptId;
    private Integer questionAttemptSeq;
    private String userAnswer;
    private Long hintId;
    private Hint hint;
    private Double grade;
    private Integer resultFlag;
    private Integer flagged;
    private Integer completed;
    private Integer attemptsRemained;
    private Date timeStart;
    private Date timeFinish;
    private Date timeSubmit;
    private String uuidFlag;
    private Integer certainty;
    private String userIdea;
    private Double awardRate;
    private Double awardPoint;
    private String teacherFeedback;
    private Integer replyStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public UserQuizAttempt getUserQuizAttempt() {
        return userQuizAttempt;
    }

    public void setUserQuizAttempt(UserQuizAttempt userQuizAttempt) {
        this.userQuizAttempt = userQuizAttempt;
    }

    public Long getUserQuizAttemptId() {
        return userQuizAttemptId;
    }

    public void setUserQuizAttemptId(Long userQuizAttemptId) {
        this.userQuizAttemptId = userQuizAttemptId;
    }

    public Integer getQuestionAttemptSeq() {
        return questionAttemptSeq;
    }

    public void setQuestionAttemptSeq(Integer questionAttemptSeq) {
        this.questionAttemptSeq = questionAttemptSeq;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public Long getHintId() {
        return hintId;
    }

    public void setHintId(Long hintId) {
        this.hintId = hintId;
    }

    public Hint getHint() {
        return hint;
    }

    public void setHint(Hint hint) {
        this.hint = hint;
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

    public Integer getFlagged() {
        return flagged;
    }

    public void setFlagged(Integer flagged) {
        this.flagged = flagged;
    }

    public Integer getCompleted() {
        return completed;
    }

    public void setCompleted(Integer completed) {
        this.completed = completed;
    }

    public Integer getAttemptsRemained() {
        return attemptsRemained;
    }

    public void setAttemptsRemained(Integer attemptsRemained) {
        this.attemptsRemained = attemptsRemained;
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

    public Date getTimeSubmit() {
        return timeSubmit;
    }

    public void setTimeSubmit(Date timeSubmit) {
        this.timeSubmit = timeSubmit;
    }

    public String getUuidFlag() {
        return uuidFlag;
    }

    public void setUuidFlag(String uuidFlag) {
        this.uuidFlag = uuidFlag;
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

    public Integer getReplyStatus() {
        return replyStatus;
    }

    public void setReplyStatus(Integer replyStatus) {
        this.replyStatus = replyStatus;
    }
}