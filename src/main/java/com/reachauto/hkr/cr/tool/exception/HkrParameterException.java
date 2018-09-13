package com.reachauto.hkr.cr.tool.exception;

/**
 * Created with IntelliJ IDEA.
 * User: chenxiangning
 * Date: 2017-12-28 11:27
 * This is my work in reachauto code.
 * mail:chenxiangning@reachauto.com
 * Description: 请求参数异常
 */
public class HkrParameterException extends HkrClientException {


    private static final long serialVersionUID = -6164729939471924058L;

    public HkrParameterException() {
        super(GlobalExceptionCode.APP_SIDE_PARAMETER_EXCEPTION, GlobalExceptionCode.APP_SIDE_PARAMETER_EXCEPTION_MSG);
    }
    public HkrParameterException(int code) {
        super(code);
    }

    public HkrParameterException(String msg) {
        super(GlobalExceptionCode.APP_SIDE_PARAMETER_EXCEPTION, msg);
    }

    public HkrParameterException(int code, String description) {
        super(code, description);
    }

    public HkrParameterException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public HkrParameterException(int code, String description, Throwable cause) {
        super(code, description, cause);
    }

}
