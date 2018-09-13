package com.reachauto.hkr.cr.tool.exception;


/**
 * Created with IntelliJ IDEA.
 * User: chenxiangning
 * Date: 2017-12-28 11:27
 * This is my work in reachauto code.
 * mail:chenxiangning@reachauto.com
 * Description: 客户端引起的异常可以使用它
 */
public class HkrClientException extends HkrRuntimeException {

    private static final long serialVersionUID = 6666556043522681444L;

    public HkrClientException() {
        super(GlobalExceptionCode.APP_SIDE_EXCEPTION, String.format("[%s]", GlobalExceptionCode.APP_SIDE_EXCEPTION_MSG));
    }

    public HkrClientException(int code) {
        super(code, String.format("[%s]", GlobalExceptionCode.APP_SIDE_EXCEPTION_MSG));
    }

    public HkrClientException(String description) {
        super(GlobalExceptionCode.APP_SIDE_EXCEPTION, String.format("%s", description));
    }

    public HkrClientException(int code, String description) {
        super(code, String.format("%s", description));
    }

    public HkrClientException(String description, Throwable cause) {
        super(String.format("%s", description), cause);
    }

    public HkrClientException(int code, String description, Throwable cause) {
        super(code, String.format("%s", description), cause);
    }

}
