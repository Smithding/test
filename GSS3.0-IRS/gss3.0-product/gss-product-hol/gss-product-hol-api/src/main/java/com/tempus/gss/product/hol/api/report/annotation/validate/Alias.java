package com.tempus.gss.product.hol.api.report.annotation.validate;

import java.lang.annotation.*;

/**
 * 别名：
 * @author huangjinhui
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Alias {

	/**
	 * 嵌套注解
	 * @return
	 */
	AliasItem[] values();
}
