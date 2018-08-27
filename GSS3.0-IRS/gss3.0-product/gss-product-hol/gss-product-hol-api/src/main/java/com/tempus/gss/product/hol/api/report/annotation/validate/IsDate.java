package com.tempus.gss.product.hol.api.report.annotation.validate;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2018/8/24.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IsDate {

    /**
     * 提示信息
     * @return
     */
    String message() default "";
}
