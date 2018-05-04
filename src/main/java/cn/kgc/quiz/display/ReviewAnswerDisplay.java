package cn.kgc.quiz.display;

public class ReviewAnswerDisplay {
    private Long id;
    private Integer answerOrder;
    private String answer = "";
    private String feedback;
    private Integer selected;
    private Integer resultFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAnswerOrder() {
        return answerOrder;
    }

    public void setAnswerOrder(Integer answerOrder) {
        this.answerOrder = answerOrder;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }

    public Integer getResultFlag() {
        return resultFlag;
    }

    public void setResultFlag(Integer resultFlag) {
        this.resultFlag = resultFlag;
    }
}
