package com.reachauto.hkr.cr.entity;

import com.reachauto.hkr.cr.pojo.bo.BalanceRecordsBO;
import com.reachauto.hkr.cr.pojo.enu.BalanceTypeEnum;
import com.reachauto.hkr.cr.pojo.parameter.AccountModifyParameter;
import lombok.Data;
import lombok.ToString;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2018/2/22.
 */
@Data
@ToString(callSuper = true)
public class BalanceRecordDO extends Entity {

    private static final String ROLLBACK_PREFIX = "rollback-";

    private String userId;
    private Integer balanceType;
    private BigDecimal change;
    private BigDecimal balanceAmount;
    private String uuid;
    private Integer rollbackFlag;

    /**
     * todo 将普通余额映射到通用余额bean中，待余额迁表后删除
     */
    public static BalanceRecordDO buildFromBalanceRecordsBO(BalanceRecordsBO balanceRecordsBO) {
        if (balanceRecordsBO == null) {
            return null;
        }
        BalanceRecordDO balanceRecordDO= new BalanceRecordDO();
        balanceRecordDO.setUserId(balanceRecordsBO.getUserId());
        balanceRecordDO.setBalanceType(BalanceTypeEnum.COMMON_BALANCE.getCode());
        balanceRecordDO.setChange(balanceRecordsBO.getChange());
        balanceRecordDO.setBalanceAmount(balanceRecordsBO.getBalance());
        balanceRecordDO.setUuid(balanceRecordsBO.getUuid());
        balanceRecordDO.setRollbackFlag(balanceRecordsBO.getRollbackFlag());
        balanceRecordDO.setId(balanceRecordsBO.getId());
        balanceRecordDO.setRemarks(balanceRecordsBO.getRemarks());
        balanceRecordDO.setDeleted(balanceRecordsBO.getDeleted());
        balanceRecordDO.setCreatedBy(balanceRecordsBO.getCreatedBy());
        balanceRecordDO.setCreatedAt(balanceRecordsBO.getCreatedAt());
        balanceRecordDO.setUpdatedBy(balanceRecordsBO.getUpdatedBy());
        balanceRecordDO.setUpdatedAt(balanceRecordsBO.getUpdatedAt());
        return balanceRecordDO;
    }

    /**
     * todo 将普通余额映射到通用余额bean中，待余额迁表后删除
     */
    public static List<BalanceRecordDO> buildFromBalanceRecordsBOList(List<BalanceRecordsBO> balanceRecordsBOList) {
        if (ObjectUtils.isEmpty(balanceRecordsBOList)) {
            return Collections.emptyList();
        }
        List<BalanceRecordDO> balanceRecordDOList = new ArrayList<>();
        balanceRecordsBOList.forEach(balanceRecordsBO -> balanceRecordDOList.add(buildFromBalanceRecordsBO(balanceRecordsBO)));
        return balanceRecordDOList;
    }

    public static BalanceRecordDO buildFromAccountModifyParameter(AccountModifyParameter parameter) {
        if (parameter == null) {
            return null;
        }
        BalanceRecordDO balanceRecordDO = new BalanceRecordDO();
        balanceRecordDO.setUserId(String.valueOf(parameter.getUserId()));
        balanceRecordDO.setBalanceType(Integer.valueOf(parameter.getBalanceType()));
        balanceRecordDO.setChange(parameter.getAmount());
        balanceRecordDO.setUuid(parameter.getUuid());
        balanceRecordDO.setRemarks(parameter.getRemarks());
        return balanceRecordDO;
    }

    public BalanceRecordDO getRollbackRecordDO() {
        BalanceRecordDO newRecord = new BalanceRecordDO();
        newRecord.setUserId(this.getUserId());
        newRecord.setBalanceType(this.getBalanceType());
        newRecord.setUuid(ROLLBACK_PREFIX + this.getUuid());
        newRecord.setChange(this.getChange().negate());
        newRecord.setRemarks("rollback");
        return newRecord;
    }
}
