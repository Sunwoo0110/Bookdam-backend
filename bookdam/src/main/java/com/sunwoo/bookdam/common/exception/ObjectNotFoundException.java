package com.sunwoo.bookdam.common.exception;

/**
 * @author : tonyson
 * @date : 2021/11/02 11:41 오전
 * @desc :
 */
public class ObjectNotFoundException extends RuntimeException{
    public ObjectNotFoundException() {
        super();
    }

    public ObjectNotFoundException(String message) {
        super(message);
    }

    public ObjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}