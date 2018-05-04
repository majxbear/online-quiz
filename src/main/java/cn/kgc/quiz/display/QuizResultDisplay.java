package cn.kgc.quiz.display;

public class QuizResultDisplay {
    private Object id;
    private Object group;
    private Object username;
    private Object realName;
    private Object sumGrades;
    private Object state;
    private Object timeStart;
    private Object timeFinish;
    private Object timeUsed;

    public QuizResultDisplay() {
    }

    public QuizResultDisplay(Object id, Object group, Object username, Object realName, Object sumGrades, Object state, Object timeStart, Object timeFinish, Object timeUsed) {
        this.id = id;
        this.group = group;
        this.username = username;
        this.realName = realName;
        this.sumGrades = sumGrades;
        this.state = state;
        this.timeStart = timeStart;
        this.timeFinish = timeFinish;
        this.timeUsed = timeUsed;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Object getGroup() {
        return group;
    }

    public void setGroup(Object group) {
        this.group = group;
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

    public Object getSumGrades() {
        return sumGrades;
    }

    public void setSumGrades(Object sumGrades) {
        this.sumGrades = sumGrades;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public Object getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Object timeStart) {
        this.timeStart = timeStart;
    }

    public Object getTimeFinish() {
        return timeFinish;
    }

    public void setTimeFinish(Object timeFinish) {
        this.timeFinish = timeFinish;
    }

    public Object getTimeUsed() {
        return timeUsed;
    }

    public void setTimeUsed(Object timeUsed) {
        this.timeUsed = timeUsed;
    }
}
