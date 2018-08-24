package com.tempus.gss.product.hol.api.report.annotation.validate;

import java.lang.annotation.*;

/**
 * 范围
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Range {
	/**
	 * 最小值
	 * @return
	 */
	int min();
	
	/**
	 * 最大值
	 * @return
	 */
	int max();
	
	String message() default "";
}
