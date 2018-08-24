package com.tempus.gss.product.hol.api.report.annotation.util;

import java.lang.annotation.*;

/**
 * 
 * 这里是导入对象的annoation
 * 所有需要导入的字段需要使用该注解
 * 
 * @author huangjinhui
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelImportField {

	 /**
	  * 设置排序 可以手动调整标题的顺序
	  * @return
	  */
	 int order() default 9999;
}
