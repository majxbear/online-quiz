package cn.kgc.quiz.exception;


/**
 * Description:<br>
 * Copyright:   Copyright (c) 2005<br>
 * Company:     THETI<br>
 *
 * @author ¡ı”¢»∫
 * @version 1.0,2006-7-13
 */
public class BasicException extends Exception{
    private String messageKey = "label.exception.default";
    private Exception innerException;

    public BasicException(){}

    public BasicException(String messageKey){
        this.messageKey = messageKey;
    }

    public BasicException(String messageKey, Exception e){
        this.messageKey = messageKey;
        this.innerException = e;
    }

    public BasicException(Exception e){
        this.messageKey = "label.exception.system";
        this.innerException = e;
    }

    public Exception getInnerException() {
        return innerException;
    }

    public void setInnerException(Exception innerException) {
        this.innerException = innerException;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getMessage() {
        if(innerException!=null)
            return innerException.getMessage();
        return super.getMessage();    
    }

    public Throwable getCause() {
            return innerException;
    }
}
