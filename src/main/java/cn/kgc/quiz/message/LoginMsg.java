package cn.kgc.quiz.message;

public class LoginMsg {
    private String username;
    private int type;
    private String token;

    public LoginMsg(String username, int type, String token) {
        this.username = username;
        this.type = type;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
