package com.reachauto.hkr.cr.pojo.bo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class BalanceRecordsBO {
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
}