package cn.kgc.quiz.display;


import java.util.List;

public class FeedbackDisplay {
    private Integer resultFlag;
    private Double grade;
    private Long questionAttemptId;
    private String generalFeedback = "";
    private String hint = "";
    private Integer attemptsRemained;
    private List<AnswerFeedbackDisplay> answers;

    public FeedbackDisplay() {
    }


    public Integer getResultFlag() {
        return resultFlag;
    }

    public void setResultFlag(Integer resultFlag) {
        this.resultFlag = resultFlag;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public Long getQuestionAttemptId() {
        return questionAttemptId;
    }

    public void setQuestionAttemptId(Long questionAttemptId) {
        this.questionAttemptId = questionAttemptId;
    }

    public String getGeneralFeedback() {
        return generalFeedback;
    }

    public void setGeneralFeedback(String generalFeedback) {
        this.generalFeedback = generalFeedback;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public Integer getAttemptsRemained() {
        return attemptsRemained;
    }

    public void setAttemptsRemained(Integer attemptsRemained) {
        this.attemptsRemained = attemptsRemained;
    }

    public List<AnswerFeedbackDisplay> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerFeedbackDisplay> answers) {
        this.answers = answers;
    }
}
