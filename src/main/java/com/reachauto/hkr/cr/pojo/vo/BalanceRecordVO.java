package com.reachauto.hkr.cr.pojo.vo;
import com.google.common.collect.Lists;

import com.reachauto.hkr.cr.entity.BalanceRecordDO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2018/2/22.
 */
@Data
public class BalanceRecordVO {
    @ApiModelProperty(value = "主键id", required = true)
    private Long id;

    @ApiModelProperty(value = "用户id", required = true)
    private String userId;
    @ApiModelProperty(value = "余额类型：1.普通余额，2.保证金余额", required = true)
    private Integer balanceType;
    @ApiModelProperty(value = "余额变化值,正数为存入,负数为支出消费", required = true)
    private BigDecimal change;
    @ApiModelProperty(value = "异动后,用户账户余额", required = true)
    private BigDecimal balanceAmount;
    @ApiModelProperty(value = "该记录是否已回滚", required = true)
    private Integer rollbackFlag;
    @ApiModelProperty(value = "备注说明", required = true)
    private String remarks;

    public static List<BalanceRecordVO> buildFromBalanceRecordDOList(List<BalanceRecordDO> balanceRecordDOList) {
        if (ObjectUtils.isEmpty(balanceRecordDOList)) {
            return Collections.emptyList();
        }

        List<BalanceRecordVO> balanceRecordVOList = new ArrayList<>();
        balanceRecordDOList.forEach(balanceRecordDO -> balanceRecordVOList.add(convertFromBalanceRecordDO(balanceRecordDO)));
        return balanceRecordVOList;

    }

    private static BalanceRecordVO convertFromBalanceRecordDO(BalanceRecordDO balanceRecordDO) {
        if (balanceRecordDO == null) {
            return null;
        }
        BalanceRecordVO balanceRecordVO= new BalanceRecordVO();
        balanceRecordVO.setId(balanceRecordDO.getId());
        balanceRecordVO.setUserId(balanceRecordDO.getUserId());
        balanceRecordVO.setBalanceType(balanceRecordDO.getBalanceType());
        balanceRecordVO.setChange(balanceRecordDO.getChange());
        balanceRecordVO.setBalanceAmount(balanceRecordDO.getBalanceAmount());
        balanceRecordVO.setRollbackFlag(balanceRecordDO.getRollbackFlag());
        balanceRecordVO.setRemarks(balanceRecordDO.getRemarks());
        return balanceRecordVO;
    }

}
