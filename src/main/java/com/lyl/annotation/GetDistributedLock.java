package com.lyl.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 【 木子雷 】 公众号
 * @PACKAGE_NAME: com.lyl.annotation
 * @ClassName: GetDistributedLock
 * @Description: 获取redis分布式锁 注解
 * @Date: 2020-11-10 16:24
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GetDistributedLock {

    // 分布式锁 key
    String lockKey();

    // 分布式锁 value，默认为 lockValue
    String lockValue() default "lockValue";

    // 过期时间，默认为 300秒
    int expireTime() default 300;

}
