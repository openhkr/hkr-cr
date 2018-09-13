package com.reachauto.hkr.cr.entity;

import com.reachauto.hkr.cr.pojo.parameter.AccountModifyParameter;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Deng Fangzhi
 * on 2018/1/30
 */
@Data
public class UserDO {

    private Long userId;

    private BigDecimal change;

    private String updateBy;

    public static UserDO buildFromAccountModifyParameter(AccountModifyParameter parameter) {
        if (parameter == null) {
            return null;
        }
        UserDO userDO = new UserDO();
        userDO.setUserId(Long.valueOf(parameter.getUserId()));
        userDO.setUpdateBy(String.valueOf(parameter.getUserId()));
        userDO.setChange(parameter.getAmount());
        return userDO;
    }

    public static UserDO buildFromBalanceRecordDOForRollback(BalanceRecordDO balanceRecordDO) {
        if (balanceRecordDO == null) {
            return null;
        }
        UserDO userDO = new UserDO();
        userDO.setUserId(Long.valueOf(balanceRecordDO.getUserId()));
        userDO.setUpdateBy("balance_rollback");
        userDO.setChange(balanceRecordDO.getChange().negate());
        return userDO;
    }
}
