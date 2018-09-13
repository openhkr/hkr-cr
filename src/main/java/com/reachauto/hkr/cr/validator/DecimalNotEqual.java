package com.reachauto.hkr.cr.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Deng Fangzhi
 * on 2018/1/29
 */
@Target({ElementType.METHOD,ElementType.FIELD})  //注解作用域
@Retention(RetentionPolicy.RUNTIME)  //注解作用时间
@Constraint(validatedBy = DecimalNotEqualValidator.class) //执行校验逻辑的类
public @interface DecimalNotEqual {

    String message() default "余额变化不能为0";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    double[] excludes() default {};
}
