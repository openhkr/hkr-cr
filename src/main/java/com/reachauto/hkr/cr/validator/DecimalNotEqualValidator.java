package com.reachauto.hkr.cr.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author Deng Fangzhi
 * on 2018/1/29
 */
public class DecimalNotEqualValidator implements ConstraintValidator<DecimalNotEqual, BigDecimal> {

    private static final String COMMA = ",";
    private double[] excludes;

    @Override
    public void initialize(DecimalNotEqual decimalNotEqualValidate) {

        excludes = decimalNotEqualValidate.excludes();
    }

    @Override
    public boolean isValid(BigDecimal input, ConstraintValidatorContext context) {

        if (Objects.isNull(input)) {
            return true;
        }
        if (null == excludes || excludes.length == 0) {
            return true;
        }
        for (double exclude : excludes) {
            if (input.compareTo(new BigDecimal(exclude)) == 0) {
                return false;
            }
        }
        return true;
    }
}
