package cn.kgc.quiz.display;

public class AnswerFeedbackDisplay {
    private Long id;
    private Integer resultFlag;
    private String specialFeedback = "";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getResultFlag() {
        return resultFlag;
    }

    public void setResultFlag(Integer resultFlag) {
        this.resultFlag = resultFlag;
    }

    public String getSpecialFeedback() {
        return specialFeedback;
    }

    public void setSpecialFeedback(String specialFeedback) {
        this.specialFeedback = specialFeedback;
    }
}
