package cn.kgc.quiz.display;

public class QuestionResultDisplay {
    private Long id;
    private String name;
    private Integer answeredNum;
    private Integer wrongNum;
    private Double wrongRate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAnsweredNum() {
        return answeredNum;
    }

    public void setAnsweredNum(Integer answeredNum) {
        this.answeredNum = answeredNum;
    }

    public Integer getWrongNum() {
        return wrongNum;
    }

    public void setWrongNum(Integer wrongNum) {
        this.wrongNum = wrongNum;
    }

    public Double getWrongRate() {
        return wrongRate;
    }

    public void setWrongRate(Double wrongRate) {
        this.wrongRate = wrongRate;
    }
}
