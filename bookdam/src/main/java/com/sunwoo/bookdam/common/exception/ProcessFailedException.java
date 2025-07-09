package com.sunwoo.bookdam.common.exception;

/**
 * @author : tonyson
 * @date : 2021/03/26 6:28 오후
 * @desc :
 */
public class ProcessFailedException extends RuntimeException{
    public ProcessFailedException() {
        super();
    }

    public ProcessFailedException(String message) {
        super(message);
    }

    public ProcessFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
