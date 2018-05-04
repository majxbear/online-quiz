package cn.kgc.quiz.domain;

public class QuestionType implements java.io.Serializable {
    private Long id;
    private String codeNumber;
    private String typeName;
    private Integer status;
    private String intro;
    private Integer whetherDefault;
    private String shortName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeNumber() {
        return codeNumber;
    }

    public void setCodeNumber(String codeNumber) {
        this.codeNumber = codeNumber;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Integer getWhetherDefault() {
        return whetherDefault;
    }

    public void setWhetherDefault(Integer whetherDefault) {
        this.whetherDefault = whetherDefault;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}