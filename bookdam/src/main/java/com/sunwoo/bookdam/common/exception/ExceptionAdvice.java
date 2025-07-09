package com.sunwoo.bookdam.common.exception;

import com.sunwoo.bookdam.common.model.response.BaseResponse;
import com.sunwoo.bookdam.common.model.response.CommonBaseResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice(basePackages = "com.sunwoo.bookdam")
public class ExceptionAdvice {

    private final BaseResponse baseResponse;
    private final MessageSource messageSource;

    // 알 수 없는 예외
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonBaseResult defaultException(HttpServletRequest request, Exception e) {
        log.error("[Unhandled Exception]", e);
        return baseResponse.getFailResult(
                Integer.parseInt(getMessage("unKnown.code")),
                getMessage("unKnown.message")
        );
    }

    // 유효성 검사 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonBaseResult handleValidationException(MethodArgumentNotValidException e) {
        String message = getMessage("argValidationFailed.message") + " (" + e.getBindingResult() + ")";
        return baseResponse.getFailResult(
                Integer.parseInt(getMessage("argValidationFailed.code")), message
        );
    }

    // JSON 파싱 실패
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonBaseResult handleJsonParseException(HttpMessageNotReadableException e) {
        String message = getMessage("argValidationFailed.message");
        if (e.getMessage() != null) message += " (" + e.getMessage() + ")";
        return baseResponse.getFailResult(
                Integer.parseInt(getMessage("argValidationFailed.code")), message
        );
    }

    // 인증 관련
    @ExceptionHandler({
            AuthExpireException.class,
            AuthAccessDeniedException.class,
            AuthEntryPointException.class,
            AuthTokenExpiredException.class,
            AuthFailedException.class,
            AccessDeniedException.class,
            SigninFailedException.class
    })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected CommonBaseResult handleAuthExceptions(Exception e) {
        String code = "authfailed.code";
        String message = "authfailed.message";

        if (e instanceof AuthExpireException) {
            code = "authExpire.code"; message = "authExpire.message";
        } else if (e instanceof AuthAccessDeniedException || e instanceof AccessDeniedException) {
            code = "accessDenied.code"; message = "accessDenied.message";
        } else if (e instanceof AuthEntryPointException) {
            code = "entryPointException.code"; message = "entryPointException.message";
        } else if (e instanceof AuthTokenExpiredException) {
            code = "tokenExpired.code"; message = "tokenExpired.message";
        } else if (e instanceof SigninFailedException) {
            code = "signinFailed.code"; message = "signinFailed.message";
        }

        return baseResponse.getFailResult(
                Integer.parseInt(getMessage(code)), getMessage(message)
        );
    }

    // 비즈니스 예외 (요청 잘못 등)
    @ExceptionHandler({
            InvalidRequstException.class,
            ProcessFailedException.class,
            ObjectAlreadExistException.class,
            ObjectNotFoundException.class,
            UserNotFoundException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonBaseResult handleBusinessExceptions(Exception e) {
        String code = "processFailed.code";
        String message = "processFailed.message";

        if (e instanceof InvalidRequstException) {
            code = "invalidRequest.code"; message = "invalidRequest.message";
        } else if (e instanceof ObjectAlreadExistException) {
            code = "objectAlreadyExist.code"; message = "objectAlreadyExist.message";
        } else if (e instanceof ObjectNotFoundException) {
            code = "objectNotFound.code"; message = "objectNotFound.message";
        } else if (e instanceof UserNotFoundException) {
            code = "userNotFound.code"; message = "userNotFound.message";
        }

        if (e.getMessage() != null) {
            message = getMessage(message) + " (" + e.getMessage() + ")";
        } else {
            message = getMessage(message);
        }
        return baseResponse.getFailResult(
                Integer.parseInt(getMessage(code)), message
        );
    }

    // 메시지 코드에 맞는 다국어 메시지 조회
    private String getMessage(String code) {
        return getMessage(code, null);
    }

    private String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
