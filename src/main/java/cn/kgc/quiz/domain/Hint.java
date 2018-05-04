package cn.kgc.quiz.domain;


public class Hint implements java.io.Serializable {

    private Long id;
    private Question question;
    private Long questionId;
    private String hint;
    private Integer hintOrder;

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

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public Integer getHintOrder() {
        return hintOrder;
    }

    public void setHintOrder(Integer hintOrder) {
        this.hintOrder = hintOrder;
    }
}