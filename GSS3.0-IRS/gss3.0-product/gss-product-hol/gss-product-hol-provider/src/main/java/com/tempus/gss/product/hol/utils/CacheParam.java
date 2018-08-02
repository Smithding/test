package com.tempus.gss.product.hol.utils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 锁的参数
 * @author Cassini
 *
 */
@Target({ElementType.PARAMETER,ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CacheParam {
	
	/**
     * 字段名称
     *
     * @return String
     */
    String name() default "";
    
    String id() default "";
    
}
