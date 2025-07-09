package com.sunwoo.bookdam.common.exception;

public class ObjectAlreadExistException extends RuntimeException{
    public ObjectAlreadExistException(String msg, Throwable t) {
        super(msg, t);
    }

    public ObjectAlreadExistException(String msg) {
        super(msg);
    }

    public ObjectAlreadExistException() { super(); }
}
