package com.br.springsecurity.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String level = "ERROR";

    public CustomException(String msg) {
        super(msg);
    }

    public CustomException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public CustomException(String msg, String level) {
        super(msg);
        this.level = level;
    }

    public CustomException(String msg, Throwable cause, String level) {
        super(msg, cause);
        this.level = level;
    }

    public String getLevel() {
        return level;
    }
}
