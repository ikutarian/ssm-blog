package cn.javaex.blog.exception;

import lombok.Getter;

@Getter
public class QingException extends Exception {

    private String message;

    public QingException(String message) {
        super(message);
        this.message = message;
    }
}
