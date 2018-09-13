package com.reachauto.hkr.cr.tool.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created with IntelliJ IDEA.
 * User: chenxiangning
 * Date: 2017-09-09 14:26
 * This is my work in reachauto code.
 * mail:chenxiangning@reachauto.com
 * Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class RestApiValidationError implements ApiSubError {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    public RestApiValidationError(String object, String message) {
        this.object = object;
        this.message = message;
    }


}