package com.lyl.aop;

import com.lyl.annotation.GetDistributedLock;
import com.lyl.service.RedisService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: 【 木子雷 】 公众号
 * @PACKAGE_NAME: com.lyl.aop
 * @ClassName: DistributedLockAspect
 * @Description: 自定义注解结合AOP切面编程优雅的使用分布式锁
 * @Date: 2020-11-10 16:52
 **/
@Component
@Aspect
public class DistributedLockAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RedisService redisService;


    /**
     * Around 环绕增强通知
     *
     * @param joinPoint 连接点，所有方法都属于连接点；但是当某些方法上使用了@GetDistributedLock自定义注解时，
     *                  则其将连接点变为了切点；然后在切点上织入额外的增强处理；切点和其相应的增强处理构成了切面Aspect 。
     */
    @Around(value = "@annotation(com.lyl.annotation.GetDistributedLock)")
    public Boolean handlerDistributedLock(ProceedingJoinPoint joinPoint) {
        // 通过反射获取自定义注解对象
        GetDistributedLock getDistributedLock = ((MethodSignature) joinPoint.getSignature())
                .getMethod().getAnnotation(GetDistributedLock.class);

        // 获取自定义注解对象中的属性值
        String lockKey = getDistributedLock.lockKey();
        String LockValue = getDistributedLock.lockValue();
        int expireTime = getDistributedLock.expireTime();

        if (redisService.tryGetDistributedLock(lockKey, LockValue, expireTime)) {
            // 获取分布式锁成功后，继续执行业务逻辑
            try {
                return (boolean) joinPoint.proceed();
            } catch (Throwable throwable) {
                logger.error("业务逻辑执行失败。", throwable);
            } finally {
                // 最终保证分布式锁的释放
                redisService.releaseDistributedLock(lockKey, LockValue);
            }
        }
        return false;
    }

}
