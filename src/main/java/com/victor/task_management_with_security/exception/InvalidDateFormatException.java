package com.victor.task_management_with_security.exception;

public class InvalidDateFormatException extends RuntimeException {
    public InvalidDateFormatException(String msg){
        super(msg);
    }
}
