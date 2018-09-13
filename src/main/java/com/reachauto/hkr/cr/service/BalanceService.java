package com.reachauto.hkr.cr.service;

import com.reachauto.hkr.cr.pojo.parameter.AccountModifyParameter;
import com.reachauto.hkr.cr.pojo.vo.BalanceRecordStatusVO;

/**
 * @author Deng Fangzhi
 * on 2018/1/29
 */
public interface BalanceService {

    void modifyBalance(AccountModifyParameter accountModifyParameter);

    void rollback(String uuid);

    BalanceRecordStatusVO getRecordStatus(String uuid);
}
