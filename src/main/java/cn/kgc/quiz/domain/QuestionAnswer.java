package cn.kgc.quiz.domain;


public class QuestionAnswer implements java.io.Serializable {

    private Long id;
    private Question question;
    private Long questionId;
    private String answer;
    private Double grade;
    private String feedback;
    private Integer answerOrder;
    private Integer rightAnswer;


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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Integer getAnswerOrder() {
        return answerOrder;
    }

    public void setAnswerOrder(Integer answerOrder) {
        this.answerOrder = answerOrder;
    }

    public Integer getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(Integer rightAnswer) {
        this.rightAnswer = rightAnswer;
    }
}