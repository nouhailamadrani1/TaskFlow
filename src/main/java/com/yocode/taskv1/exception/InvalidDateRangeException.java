package com.yocode.taskv1.exception;

public class InvalidDateRangeException extends RuntimeException{
    public InvalidDateRangeException(String message) {
        super(message);
    }
}
