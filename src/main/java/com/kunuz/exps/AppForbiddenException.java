package com.kunuz.exps;

public class AppForbiddenException extends RuntimeException {
    public AppForbiddenException(String message) {
        super(message);
    }
}
