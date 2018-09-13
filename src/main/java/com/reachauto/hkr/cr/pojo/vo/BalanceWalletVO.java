package com.reachauto.hkr.cr.pojo.vo;

import com.reachauto.hkr.cr.entity.BalanceWalletDO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/2/22.
 */
@Data
public class BalanceWalletVO {

    @ApiModelProperty(value = "用户id", required = true)
    private String userId;

    @ApiModelProperty(value = "余额类型：1.普通余额，2.保证金余额", required = true)
    private Integer balanceType;

    @ApiModelProperty(value = "余额总额", required = true)
    private BigDecimal amount;

    public static BalanceWalletVO buildFromBalanceWalletDO(BalanceWalletDO balanceWalletDO) {
        BalanceWalletVO balanceWalletVO= new BalanceWalletVO();
        balanceWalletVO.setUserId(balanceWalletDO.getUserId());
        balanceWalletVO.setBalanceType(balanceWalletDO.getBalanceType());
        balanceWalletVO.setAmount(balanceWalletDO.getAmount());
        return balanceWalletVO;
    }
}
