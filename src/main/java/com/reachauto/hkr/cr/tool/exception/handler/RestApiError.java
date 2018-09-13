package com.reachauto.hkr.cr.tool.exception.handler;

import lombok.Data;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: chenxiangning
 * Date: 2017-09-09 14:19
 * This is my work in reachauto code.
 * mail:chenxiangning@reachauto.com
 * Description:
 */
@Data
public class RestApiError {
    private int code;
    private String description;
    private List tips;
    private long lastUpdateTime = 0L;

    private RestApiError() {
        lastUpdateTime = System.currentTimeMillis();
    }

    public RestApiError(Throwable ex) {
        this();
        this.description = ex.getLocalizedMessage();

    }

    public RestApiError(String description, Throwable ex) {
        this();
        this.description = description;
    }

    private void addSubError(ApiSubError subError) {
        if (tips == null) {
            tips = new ArrayList<>();
        }
        tips.add(subError);
    }

    public void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
        constraintViolations.forEach(this::addValidationError);
    }

    public void addValidationError(List<ObjectError> globalErrors) {
        globalErrors.forEach(this::addValidationError);
    }

    public void addValidationErrors(List<FieldError> fieldErrors) {
        fieldErrors.forEach(this::addValidationError);
    }


    private void addValidationError(String object, String field, Object rejectedValue, String message) {
        addSubError(new RestApiValidationError(object, field, rejectedValue, message));
    }

    private void addValidationError(String object, String message) {
        addSubError(new RestApiValidationError(object, message));
    }

    private void addValidationError(FieldError fieldError) {
        this.addValidationError(
                fieldError.getObjectName(),
                fieldError.getField(),
                fieldError.getRejectedValue(),
                fieldError.getDefaultMessage());
    }


    private void addValidationError(ObjectError objectError) {
        this.addValidationError(
                objectError.getObjectName(),
                objectError.getDefaultMessage());
    }


    /**
     * Utility method for adding error of ConstraintViolation. Usually when a @Validated validation fails.
     *
     * @param cv the ConstraintViolation
     */
    private void addValidationError(ConstraintViolation<?> cv) {
        this.addValidationError(
                cv.getRootBeanClass().getSimpleName(),
                ((PathImpl) cv.getPropertyPath()).getLeafNode().asString(),
                cv.getInvalidValue(),
                cv.getMessage());
    }


}