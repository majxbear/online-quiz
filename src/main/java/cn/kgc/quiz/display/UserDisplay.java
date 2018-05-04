package cn.kgc.quiz.display;

public class UserDisplay {
    private Object id;
    private Object username;
    private Object realName;
    private Object email;

    public UserDisplay() {
    }

    public UserDisplay(Object id, Object username, Object realName, Object email) {
        this.id = id;
        this.username = username;
        this.realName = realName;
        this.email = email;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Object getUsername() {
        return username;
    }

    public void setUsername(Object username) {
        this.username = username;
    }

    public Object getRealName() {
        return realName;
    }

    public void setRealName(Object realName) {
        this.realName = realName;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }
}
