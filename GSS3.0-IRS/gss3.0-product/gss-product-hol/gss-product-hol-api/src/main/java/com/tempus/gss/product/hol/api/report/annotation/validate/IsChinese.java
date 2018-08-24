package com.tempus.gss.product.hol.api.report.annotation.validate;

import java.lang.annotation.*;

/**
 * 中文
 * @author huangjinhui
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IsChinese {

	/**
	 * 提示信息
	 * @return
	 */
	String message() default "";
}
