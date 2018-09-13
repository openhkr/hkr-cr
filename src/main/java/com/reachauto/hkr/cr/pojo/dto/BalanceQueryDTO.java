package com.reachauto.hkr.cr.pojo.dto;

import com.reachauto.hkr.cr.constant.Constants;
import com.reachauto.hkr.cr.constant.ErrorCodeConstant;
import com.reachauto.hkr.cr.pojo.enu.BalanceTypeEnum;
import com.reachauto.hkr.cr.tool.exception.HkrBusinessException;
import com.reachauto.hkr.cr.tool.exception.HkrParameterException;
import com.reachauto.hkr.cr.tool.page.PageSortCondition;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Administrator on 2018/2/23.
 */
@Data
@ToString(callSuper = true)
public class BalanceQueryDTO extends PageSortCondition {
    private String userId;
    private Integer balanceType;

    public BalanceQueryDTO() {

    }

    public BalanceQueryDTO(String userId, Integer balanceType) {
        this.userId = userId;
        this.balanceType = balanceType;
    }

    public BalanceQueryDTO(String userId, Integer balanceType, PageSortCondition pageSortCondition) {
        super(pageSortCondition.getCurrentPage(), pageSortCondition.getPageSize());
        this.userId = userId;
        this.balanceType = balanceType;
    }

    public static BalanceQueryDTO verifyAndAssembleParams(String userId, String balanceType, PageSortCondition pageSortCondition) {
        Integer balanceTypeCode;
        try {
            balanceTypeCode = Integer.valueOf(balanceType);
        } catch (NumberFormatException e) {
            throw new HkrParameterException();
        }
        BalanceTypeEnum balanceTypeEnum = BalanceTypeEnum.getType(balanceTypeCode);
        if (balanceTypeEnum == null) {
            throw new HkrParameterException();
        }
        if (StringUtils.isBlank(userId)) {
            throw new HkrParameterException();
        }
        if (pageSortCondition == null) {
            return new BalanceQueryDTO(userId, balanceTypeEnum.getCode());
        }
        if (pageSortCondition.getPageSize() == null || pageSortCondition.getCurrentPage() == null || pageSortCondition.getPageSize() > Constants.MAX_RECORDS_PER_PAGE) {
            throw new HkrBusinessException(ErrorCodeConstant.PAGE_PARAMS_ERROR, "page.params.error");
        }
        return new BalanceQueryDTO(userId, balanceTypeEnum.getCode(), pageSortCondition);
    }
}
