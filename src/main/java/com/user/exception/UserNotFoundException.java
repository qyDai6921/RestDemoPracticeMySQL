package com.user.exception;

public class UserNotFoundException extends UserException {
    public UserNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
