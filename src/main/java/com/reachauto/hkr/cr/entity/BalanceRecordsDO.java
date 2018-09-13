package com.reachauto.hkr.cr.entity;

import com.reachauto.hkr.cr.pojo.parameter.AccountModifyParameter;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class BalanceRecordsDO {

    private static final String ROLLBACK_PREFIX = "rollback-";

    private Long id;

    private String uuid;

    private String userId;

    private BigDecimal change;

    private Integer sourceType;

    private String sourceId;

    private BigDecimal balance;

    private Integer rollbackFlag;

    private Date createdAt;

    private String createdBy;

    private Date updatedAt;

    private String updatedBy;

    private String remarks;

    private Integer deleted;

    private Integer oldRollbackFlag;

    public static BalanceRecordsDO buildFromAccountModifyParameter(AccountModifyParameter parameter) {
        if (parameter == null) {
            return null;
        }
        BalanceRecordsDO balanceRecordsDO = new BalanceRecordsDO();
        balanceRecordsDO.setUserId(String.valueOf(parameter.getUserId()));
        balanceRecordsDO.setUuid(parameter.getUuid());
        balanceRecordsDO.setChange(parameter.getAmount());
        balanceRecordsDO.setRemarks(parameter.getRemarks());
        balanceRecordsDO.setCreatedBy(String.valueOf(parameter.getUserId()));
        balanceRecordsDO.setUpdatedBy(String.valueOf(parameter.getUserId()));
        return balanceRecordsDO;
    }

    public static BalanceRecordsDO buildFromBalanceRecordDOForRollback(BalanceRecordDO balanceRecordDO) {
        if (balanceRecordDO == null) {
            return null;
        }
        BalanceRecordsDO newRecord = new BalanceRecordsDO();
        newRecord.setUserId(balanceRecordDO.getUserId());
        newRecord.setUuid(ROLLBACK_PREFIX + balanceRecordDO.getUuid());
        newRecord.setChange(balanceRecordDO.getChange().negate());
        newRecord.setRemarks("rollback");
        newRecord.setCreatedBy("rollback");
        newRecord.setUpdatedBy("rollback");
        return newRecord;
    }
}