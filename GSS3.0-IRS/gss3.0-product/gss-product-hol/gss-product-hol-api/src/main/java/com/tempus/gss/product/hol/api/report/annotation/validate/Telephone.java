package com.tempus.gss.product.hol.api.report.annotation.validate;

import java.lang.annotation.*;

/**
 * 电话
 * @author huangjinhui
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Telephone {

	String message() default "";
}
