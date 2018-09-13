package com.reachauto.hkr.cr.tool.exception;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: chenxiangning
 * Date: 2018-01-19 13:55
 * This is my work in reachauto code.
 * mail:chenxiangning@reachauto.com
 * Description:
 */
@Data
public class ResultExceptionBean {
    private int code;
    private String description;
    private String exception;
    private String path;

    public ResultExceptionBean(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public ResultExceptionBean(int code, String description, String exception) {
        this.code = code;
        this.description = description;
        this.exception = exception;
    }

    public ResultExceptionBean(int code, String description, String exception, String path) {
        this.code = code;
        this.description = description;
        this.exception = exception;
        this.path = path;
    }
}