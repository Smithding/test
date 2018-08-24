package com.tempus.gss.product.hol.api.report.annotation.validate;

import java.lang.annotation.*;

/**
 * 最大值
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Max {
	/**
	 * 数值
	 * @return
	 */
	int value();
	
	String message() default "";
}
