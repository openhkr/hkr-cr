package com.reachauto.hkr.cr.constant;

/**
 * @author Deng Fangzhi
 * on 2018/1/29
 */
public class ErrorCodeConstant {

    /**
     * 业务正在执行
     */
    public static final int BUSINESS_PROCESSING = 24001;

    /**
     * 业务已执行
     */
    public static final int BUSINESS_ALREADY_DONE = 24002;

    /**
     * 用户不存在
     */
    public static final int WRONG_USER_INFO = 24003;

    /**
     * 用户余额不足
     */
    public static final int BALANCE_ACCOUNT_NOT_ENOUGH = 24004;

    /**
     * 该流水不存在
     */
    public static final int UUID_NOT_EXISTS = 24005;

    /**
     * 更新用户余额错误
     */
    public static final int UPDATE_USER_ACCOUNT_ERROR = 24006;

    /**
     * 余额超出限定范围
     */
    public static final int BALANCE_OUT_OF_RANGE = 24007;

    /**
     * 分页参数错误，每页最多200条
     */
    public static final int PAGE_PARAMS_ERROR = 24008;

    /**
     * 批量查询最多支持200条
     */
    public static final int OUT_OF_QUERY_MAX_200 = 24009;


}
