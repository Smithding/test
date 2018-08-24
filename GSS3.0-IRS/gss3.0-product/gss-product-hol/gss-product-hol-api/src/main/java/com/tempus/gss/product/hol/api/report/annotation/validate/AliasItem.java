package com.tempus.gss.product.hol.api.report.annotation.validate;

import java.lang.annotation.*;

/**
 * 配合 Alias使用。source指的是 excel的数据。target指的是 对应的结果
 * @author huangjinhui
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AliasItem {
	/**
	 * 要转换的值
	 * @return
	 */
	String source();
	
	/**
	 * 转换成的值
	 * @return
	 */
	String target();
}
