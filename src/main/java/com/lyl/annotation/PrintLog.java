package com.lyl.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: 【 木子雷 】 公众号
 * @PACKAGE_NAME: com.lyl.annotation
 * @ClassName: PrintLog
 * @Description: 进行日志打印 的自定义注解
 * @Date: 2020-11-10 18:05
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PrintLog {

}
