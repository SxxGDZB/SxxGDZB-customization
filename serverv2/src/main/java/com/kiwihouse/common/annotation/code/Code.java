package com.kiwihouse.common.annotation.code;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yjzn
 * @date 2019-10-31-下午 5:02
 */
@Target({ElementType.FIELD,ElementType.METHOD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CodeConstraintValidated.class)
public @interface Code {

    String message() default "区域编码不正确";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
