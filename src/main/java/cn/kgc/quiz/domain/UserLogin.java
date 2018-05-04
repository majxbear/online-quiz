package cn.kgc.quiz.domain;

import java.util.Date;

public class UserLogin {
    private Long id;
    private String username;
    private String token;
    private Date loginTime;

    public UserLogin() {
    }

    public UserLogin(Long id, String username, String token, Date loginTime) {
        this.id = id;
        this.username = username;
        this.token = token;
        this.loginTime = loginTime;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }
}
