package cn.kgc.quiz.display;

public class IdeaDisplay {
    private Long id;
    private String username;
    private String realName;
    private Integer replyStatus;
    private String idea;

    public IdeaDisplay() {
    }

    public IdeaDisplay(Long id, String username, String realName, Integer replyStatus, String idea) {
        this.id = id;
        this.username = username;
        this.realName = realName;
        this.replyStatus = replyStatus;
        this.idea = idea;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getReplyStatus() {
        return replyStatus;
    }

    public void setReplyStatus(Integer replyStatus) {
        this.replyStatus = replyStatus;
    }

    public String getIdea() {
        return idea;
    }

    public void setIdea(String idea) {
        this.idea = idea;
    }
}
