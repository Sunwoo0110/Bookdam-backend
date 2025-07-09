package com.sunwoo.bookdam.common.exception;

public class InvalidRequstException extends RuntimeException{
    public InvalidRequstException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidRequstException(String message) {
        super(message);
    }

    public InvalidRequstException() {
    }
}
