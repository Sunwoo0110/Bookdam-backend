package com.sunwoo.bookdam.common.exception;

public class AuthAccessDeniedException extends RuntimeException{
    public AuthAccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthAccessDeniedException(String message) {
        super(message);
    }

    public AuthAccessDeniedException() {
        super();
    }
}
