package com.lyl.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author: 【 木子雷 】 公众号
 * @PACKAGE_NAME: com.lyl.aop
 * @ClassName: PrintLogAspect
 * @Description: 自定义注解结合AOP切面编程优雅的实现日志打印
 * @Date: 2020-11-10 18:11
 **/
@Component
@Aspect
public class PrintLogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Around 环绕增强通知
     *
     * @param joinPoint 连接点，所有方法都属于连接点；但是当某些方法上使用了@PrintLog自定义注解时，
     *                  则其将连接点变为了切点；然后在切点上织入额外的增强处理；切点和其相应的增强处理构成了切面Aspect 。
     */
    @Around(value = "@annotation(com.lyl.annotation.PrintLog)")
    public Object handlerPrintLog(ProceedingJoinPoint joinPoint) {
        // 获取方法的名称
        String methodName = joinPoint.getSignature().getName();
        // 获取方法入参
        Object[] param = joinPoint.getArgs();

        StringBuilder sb = new StringBuilder();
        for (Object o : param) {
            sb.append(o + "; ");
        }
        logger.info("进入《{}》方法, 参数为: {}", methodName, sb.toString());

        Object object = null;
        // 继续执行方法
        try {
            object = joinPoint.proceed();

        } catch (Throwable throwable) {
            logger.error("打印日志处理error。。", throwable);
        }
        logger.info("{} 方法执行结束。。", methodName);
        return object;
    }

}
