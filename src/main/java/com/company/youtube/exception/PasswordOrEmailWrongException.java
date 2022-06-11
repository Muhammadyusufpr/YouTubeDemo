package com.company.youtube.exception;

public class PasswordOrEmailWrongException extends RuntimeException {
    public PasswordOrEmailWrongException(String message) {
        super(message);
    }
}
