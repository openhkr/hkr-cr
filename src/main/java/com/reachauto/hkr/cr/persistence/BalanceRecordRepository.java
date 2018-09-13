package com.reachauto.hkr.cr.persistence;

import com.reachauto.hkr.cr.entity.BalanceRecordDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2018/2/22.
 */
@Mapper
public interface BalanceRecordRepository extends PagingAndSortingRepository<BalanceRecordDO> {

    BalanceRecordDO getRecordByUuid(@Param(value = "uuid") String uuid);

    int createRecord(BalanceRecordDO balanceRecordDO);

    int setHasRollback(Long id);
}
