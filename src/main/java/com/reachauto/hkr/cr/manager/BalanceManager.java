package com.reachauto.hkr.cr.manager;

import com.reachauto.hkr.cr.entity.BalanceRecordDO;
import com.reachauto.hkr.cr.entity.BalanceWalletDO;
import com.reachauto.hkr.cr.pojo.dto.BalanceQueryDTO;
import com.reachauto.hkr.cr.pojo.parameter.AccountModifyParameter;
import com.reachauto.hkr.cr.tool.page.PageListResult;

import java.util.List;

/**
 * @author Deng Fangzhi
 * on 2018/1/30
 */
public interface BalanceManager {

    void modifyBalance(AccountModifyParameter parameter);

    void rollback(BalanceRecordDO oldRecord);

    BalanceRecordDO getRecordByUuid(String uuid);

    BalanceWalletDO getBalance(BalanceQueryDTO balanceQueryDTO);

    List<BalanceWalletDO> getBalanceList(List<String> userIdList, Integer balanceType);

    PageListResult<BalanceRecordDO> getBalanceRecord(BalanceQueryDTO balanceQueryDTO);
}
