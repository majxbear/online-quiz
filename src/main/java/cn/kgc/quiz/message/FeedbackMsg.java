package cn.kgc.quiz.message;

public class FeedbackMsg {
    private String gradeFeedback;
    private double quizSumGrades;

    public FeedbackMsg(String gradeFeedback, double quizSumGrades) {
        this.gradeFeedback = gradeFeedback;
        this.quizSumGrades = quizSumGrades;
    }

    public String getGradeFeedback() {
        return gradeFeedback;
    }

    public void setGradeFeedback(String gradeFeedback) {
        this.gradeFeedback = gradeFeedback;
    }

    public double getQuizSumGrades() {
        return quizSumGrades;
    }

    public void setQuizSumGrades(double quizSumGrades) {
        this.quizSumGrades = quizSumGrades;
    }
}
