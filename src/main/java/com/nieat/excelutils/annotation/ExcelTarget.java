package com.nieat.excelutils.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: NieAnTai
 * @Description:
 * @Date: 16:29 2018/8/20
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ExcelTarget {
    /**
     * 字段名
     *
     * @return
     */
    String value() default "";

    /**
     * 日期格式
     *
     * @return
     */
    String dateFormat() default "yyyy/MM/dd HH:mm:ss";
}
