package com.tempus.gss.product.hol.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HolOrderAnnotation {
	String action() default "";
    String targetType() default "";
    String remark() default "";
}
