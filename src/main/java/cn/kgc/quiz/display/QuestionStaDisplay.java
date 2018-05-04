package cn.kgc.quiz.display;

import java.util.List;

public class QuestionStaDisplay {
    private String title;
    private List<String> categories;
    private List<Integer> answeredNum;

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

    public List<Integer> getAnsweredNum() {
        return answeredNum;
    }

    public void setAnsweredNum(List<Integer> answeredNum) {
        this.answeredNum = answeredNum;
    }
}
