package cn.kgc.quiz.display;

import java.util.List;

public class QuestionStaDisplayWithCertainty {
    private String title;
    private List<String> categories;
    private List<Integer> answeredNum0;
    private List<Integer> answeredNum1;
    private List<Integer> answeredNum2;
    private List<Integer> answeredNum3;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<Integer> getAnsweredNum0() {
        return answeredNum0;
    }

    public void setAnsweredNum0(List<Integer> answeredNum0) {
        this.answeredNum0 = answeredNum0;
    }

    public List<Integer> getAnsweredNum1() {
        return answeredNum1;
    }

    public void setAnsweredNum1(List<Integer> answeredNum1) {
        this.answeredNum1 = answeredNum1;
    }

    public List<Integer> getAnsweredNum2() {
        return answeredNum2;
    }

    public void setAnsweredNum2(List<Integer> answeredNum2) {
        this.answeredNum2 = answeredNum2;
    }

    public List<Integer> getAnsweredNum3() {
        return answeredNum3;
    }

    public void setAnsweredNum3(List<Integer> answeredNum3) {
        this.answeredNum3 = answeredNum3;
    }
}
