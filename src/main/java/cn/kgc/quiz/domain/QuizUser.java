package cn.kgc.quiz.domain;

public class QuizUser {
    private Long id;
    //0 admin, 1 normal user
    private Integer type;
    private String username;
    private String password;
    private String realname;
    private String email;

    public QuizUser() {
    }

    public QuizUser(Long id, Integer type, String username, String password, String realname, String email) {
        this.id = id;
        this.type = type;
        this.username = username;
        this.password = password;
        this.realname = realname;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
