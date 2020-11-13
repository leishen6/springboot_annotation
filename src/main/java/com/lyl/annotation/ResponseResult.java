package com.lyl.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 【 木子雷 】 公众号
 * @PACKAGE_NAME: com.lyl.annotation
 * @ClassName: ResponseResult
 * @Description: 标记方法返回值需要进行包装的 自定义注解
 * @Date: 2020-11-10 10:38
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseResult {

}
