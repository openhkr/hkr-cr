package com.reachauto.hkr.cr.persistence;

import com.reachauto.hkr.cr.entity.BalanceWalletDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2018/2/22.
 */
@Mapper
public interface BalanceWalletRepository {

    int modifyOrInsertBalance(BalanceWalletDO balanceWalletDO);

    BalanceWalletDO getBalanceByUserIdAndBalanceType(@Param(value = "userId") String userId,
                                                     @Param(value = "balanceType") Integer balanceType);

    List<BalanceWalletDO> getBalanceListByUserIdListAndBalanceType(@Param(value = "userIdList") List<String> userIdList,
                                                                   @Param(value = "balanceType") Integer balanceType);

    BigDecimal totalDepositBalance(@Param(value = "balanceType") Integer balanceType);
}
