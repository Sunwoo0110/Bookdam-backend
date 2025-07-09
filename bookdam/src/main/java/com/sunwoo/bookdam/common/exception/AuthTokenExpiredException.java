package com.sunwoo.bookdam.common.exception;

public class AuthTokenExpiredException extends RuntimeException{
    public AuthTokenExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthTokenExpiredException(String message) {
        super(message);
    }

    public AuthTokenExpiredException() {
        super();
    }
}
