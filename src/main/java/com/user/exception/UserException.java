package com.user.exception;

public class UserException extends RuntimeException {

    private String errorMessage;

    public UserException() {
        super();
    }

    public UserException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
