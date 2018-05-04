package cn.kgc.quiz.message;

public class UploadMsg {
    private String name;

    public UploadMsg(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
