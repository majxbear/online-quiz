package cn.kgc.quiz.display;

import java.util.List;

public class QuestionDisplay {
    private Long id;
    private String questionText = "";
    private Double defaultMark;
    private String qTypeName = "";
    private String qTypeShortName = "";
    private List<AnswerDisplay> answers;

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

    public List<AnswerDisplay> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDisplay> answers) {
        this.answers = answers;
    }
}
