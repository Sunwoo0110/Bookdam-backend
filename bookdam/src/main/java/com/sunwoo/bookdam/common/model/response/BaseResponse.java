package com.sunwoo.bookdam.common.model.response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class BaseResponse {
    @Autowired
    private MessageSource messageSource;

    public <T> CommonResult<T> getContentResult(T data) {
        CommonResult<T> result = new CommonResult<>();
        result.setContent(data);
        result.setCode(0);
        result.setMessage("success");
        return result;
    }

//    public <T> CommonResult<T> getContentResult(T data, int code, String message) {
//        CommonResult<T> result = new CommonResult<>();
//        result.setContent(data);
//        result.setCode(code);
//        result.setMessage(message);
//        return result;
//    }

    public CommonBaseResult getSuccessResult() {
        CommonBaseResult result = new CommonBaseResult();
        result.setCode(0);
        result.setMessage("success");
        return result;
    }

    public CommonBaseResult getFailResult(int code, String message) {
        CommonBaseResult result = new CommonBaseResult();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public String getMessage(String code) {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }
}
