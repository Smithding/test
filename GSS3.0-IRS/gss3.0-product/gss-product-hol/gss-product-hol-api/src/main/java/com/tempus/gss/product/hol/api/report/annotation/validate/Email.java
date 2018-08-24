package com.tempus.gss.product.hol.api.report.annotation.validate;

import java.lang.annotation.*;

/**
 * 邮箱
 * @author huangjinhui
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Email {
	/**
	 * 邮箱格式(默认不校验)
	 * @return
	 */
	String message() default "";
}
