package com.reachauto.hkr.cr.entity;

import com.reachauto.hkr.cr.pojo.bo.UserBO;
import com.reachauto.hkr.cr.pojo.enu.BalanceTypeEnum;
import com.reachauto.hkr.cr.pojo.parameter.AccountModifyParameter;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/22.
 */
@Data
public class BalanceWalletDO extends Entity {
    private String userId;
    private Integer balanceType;
    private BigDecimal amount;

    private BigDecimal change;
    private Integer count;

    public static BalanceWalletDO buildFromAccountModifyParameter(AccountModifyParameter parameter) {
        if (parameter == null) {
            return null;
        }
        BalanceWalletDO balanceWalletDO = new BalanceWalletDO();
        balanceWalletDO.setUserId(parameter.getUserId());
        balanceWalletDO.setBalanceType(Integer.valueOf(parameter.getBalanceType()));
        balanceWalletDO.setChange(parameter.getAmount());
        return balanceWalletDO;
    }

    public static BalanceWalletDO buildFromBalanceRecordDOForRollback(BalanceRecordDO balanceRecordDO) {
        if (balanceRecordDO == null) {
            return null;
        }
        BalanceWalletDO balanceWalletDO = new BalanceWalletDO();
        balanceWalletDO.setUserId(balanceRecordDO.getUserId());
        balanceWalletDO.setBalanceType(balanceRecordDO.getBalanceType());
        balanceWalletDO.setChange(balanceRecordDO.getChange().negate());
        return balanceWalletDO;
    }

    /**
     * todo 将普通余额映射到通用余额bean中，待余额迁表后删除
     */
    public static BalanceWalletDO buildFromUserBO(UserBO userBO) {
        if (userBO == null) {
            return null;
        }
        BalanceWalletDO balanceWalletDO = new BalanceWalletDO();
        balanceWalletDO.setUserId(userBO.getUserId().toString());
        balanceWalletDO.setBalanceType(BalanceTypeEnum.COMMON_BALANCE.getCode());
        balanceWalletDO.setAmount(userBO.getAccountBalance());
        return balanceWalletDO;
    }

    public static List<BalanceWalletDO> buildListFromUserBOList(List<UserBO> userBOList) {
        if (userBOList == null || userBOList.isEmpty()) {
            return new ArrayList<>();
        }
        List<BalanceWalletDO> balanceWalletDOList = new ArrayList<>();
        userBOList.forEach(userBO -> {
            balanceWalletDOList.add(buildFromUserBO(userBO));
        });
        return balanceWalletDOList;
    }
}
