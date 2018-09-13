package com.reachauto.hkr.cr.pojo.bo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Deng Fangzhi
 * on 2018/1/30
 */
@Data
public class UserBO {

    private BigDecimal accountBalance;

    private Long userId;
}
