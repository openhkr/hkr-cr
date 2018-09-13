package com.reachauto.hkr.cr.pojo.parameter;

import com.reachauto.hkr.cr.validator.DecimalNotEqual;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * @author Deng Fangzhi
 *         on 2018/1/29
 */
@Data
public class AccountModifyParameter {

    @ApiModelProperty(value = "用户id", hidden = true)
    @Min(0L)
    private String userId;

    @ApiModelProperty(required = true, value = "余额类型：1.普通余额；2.保证金余额")
    @NotBlank
    @Pattern(regexp = "[12]", message = "余额类型，只包括1,2")
    private String balanceType;

    @ApiModelProperty(value = "防重id", required = true)
    @NotBlank
    @Size(max = 100)
    private String uuid;

    @ApiModelProperty(value = "金额", required = true)
    @NotNull
    @Digits(integer = 6, fraction = 2)
    @DecimalNotEqual(excludes = {0d})
    private BigDecimal amount;

    @ApiModelProperty(value = "备注")
    @Size(max = 255)
    private String remarks;
}
