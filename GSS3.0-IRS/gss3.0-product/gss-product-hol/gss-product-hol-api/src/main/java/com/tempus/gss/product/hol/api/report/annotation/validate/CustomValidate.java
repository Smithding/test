package com.tempus.gss.product.hol.api.report.annotation.validate;

import java.lang.annotation.*;

/**
 * 自定义注解。regex为自定义正则表达式
 * @author huangjinhui
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomValidate {
	
	/**
	 * 正则表达式
	 * @return
	 */
	String regex();
	
	String message();

}
