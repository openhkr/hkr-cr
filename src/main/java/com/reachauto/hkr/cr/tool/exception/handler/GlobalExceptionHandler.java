package com.reachauto.hkr.cr.tool.exception.handler;

import com.reachauto.hkr.cr.tool.exception.GlobalExceptionCode;
import com.reachauto.hkr.cr.tool.exception.HkrRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.Locale;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final Locale DEFAULT_LOCALE = Locale.SIMPLIFIED_CHINESE;
    @Autowired
    private MessageSource messageSource;


    /**
     * Handles javax.validation.ConstraintViolationException. Thrown when @Validated fails.
     *
     * @param ex the ConstraintViolationException
     * @return the RestApiError object
     */
    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public RestApiError handleConstraintViolation(HttpServletRequest request, ConstraintViolationException ex) {
        RestApiError apiError = new RestApiError(ex);
        apiError.setDescription("Validation error");
        apiError.setCode(GlobalExceptionCode.APP_SIDE_PARAMETER_EXCEPTION);
        apiError.addValidationErrors(ex.getConstraintViolations());
        log.error("path:{} 堆栈信息:{}", request.getRequestURL().toString(), ex);
        return apiError;
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestApiError handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException ex) {
        RestApiError apiError = new RestApiError(ex);
        apiError.setDescription(GlobalExceptionCode.APP_SIDE_PARAMETER_EXCEPTION_MSG);
        apiError.setCode(GlobalExceptionCode.APP_SIDE_PARAMETER_EXCEPTION);
        apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
        apiError.addValidationError(ex.getBindingResult().getGlobalErrors());
        log.error("path:{} 堆栈信息:{}", request.getRequestURL().toString(), ex);
        return apiError;
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public RestApiError handleMethodArgumentTypeMismatchException(HttpServletRequest request, MethodArgumentTypeMismatchException ex) {
        RestApiError apiError = new RestApiError(ex);
        apiError.setDescription(GlobalExceptionCode.APP_SIDE_PARAMETER_EXCEPTION_MSG + ex.getMessage() + ex.getName() + ex.getParameter().toString());
        apiError.setCode(GlobalExceptionCode.APP_SIDE_PARAMETER_EXCEPTION);
        log.error("path:{} 堆栈信息:{}", request.getRequestURL().toString(), ex);
        return apiError;
    }

    @ResponseBody
    @ExceptionHandler(BindException.class)
    public RestApiError handleBindException(HttpServletRequest request, BindException ex) {
        RestApiError apiError = new RestApiError(ex);
        apiError.setDescription(GlobalExceptionCode.APP_SIDE_PARAMETER_EXCEPTION_MSG);
        apiError.setCode(GlobalExceptionCode.APP_SIDE_PARAMETER_EXCEPTION);
        apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
        apiError.addValidationError(ex.getBindingResult().getGlobalErrors());
        log.error("path:{} 堆栈信息:{}", request.getRequestURL().toString(), ex);
        return apiError;
    }

    /**
     * Handles EntityNotFoundException. Created to encapsulate errors with more detail than javax.persistence.EntityNotFoundException.
     *
     * @param ex the EntityNotFoundException
     * @return the RestApiError object
     */
    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public RestApiError handleHttpMessageNotReadableException(HttpServletRequest request, HttpMessageNotReadableException ex) {
        RestApiError apiError = new RestApiError(GlobalExceptionCode.APP_SIDE_JSON_FORMAT_EXCEPTION_MSG, ex);
        apiError.setCode(GlobalExceptionCode.APP_SIDE_JSON_FORMAT_EXCEPTION);
        log.error("path:{} 堆栈信息:{}", request.getRequestURL().toString(), ex);
        return apiError;
    }

    /**
     * Handles EntityNotFoundException. Created to encapsulate errors with more detail than javax.persistence.EntityNotFoundException.
     *
     * @param ex the EntityNotFoundException
     * @return the RestApiError object
     */
    @ResponseBody
    @ExceptionHandler(HkrRuntimeException.class)
    public RestApiError handleException(HttpServletRequest request, HkrRuntimeException ex) {
        RestApiError apiError = new RestApiError(getErrorMessage(ex.getDescription()), ex);
        apiError.setCode(ex.getCode());
        log.error("path:{} 堆栈信息:{}", request.getRequestURL().toString(), ex);
        return apiError;
    }

    /**
     * Handles EntityNotFoundException. Created to encapsulate errors with more detail than javax.persistence.EntityNotFoundException.
     *
     * @param ex the EntityNotFoundException
     * @return the RestApiError object
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public RestApiError handleException(HttpServletRequest request, Exception ex) {
        RestApiError apiError = new RestApiError(ex);
        apiError.setCode(GlobalExceptionCode.UNKNOWN_EXCEPTION);
        log.error("path:{} 堆栈信息:{}", request.getRequestURL().toString(), ex);
        return apiError;
    }

    protected String getErrorMessage(String key) {
        try {
            return this.messageSource.getMessage(key, (Object[]) null, DEFAULT_LOCALE);
        } catch (Exception var4) {
            return key;
        }
    }

    protected void printLog(String exceptionName, HttpServletRequest request, Exception ex) {
        log.error(exceptionName + " ******************************************************");
        log.error("URL:" + request.getRequestURL().toString());
        if (ex instanceof HkrRuntimeException) {
            log.error("Description:" + ((HkrRuntimeException) ex).getDescription());
        }

        log.error(ex.getLocalizedMessage());
        log.error(ex.getMessage());
        log.error("堆栈信息:", ex);
        log.error(exceptionName + " ******************************************************");
    }

}
