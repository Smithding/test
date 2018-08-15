package com.tempus.gss.product.hol.utils;

import java.lang.reflect.Method;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Aspect
@Component
public class LockMethodInterceptor {
	protected final transient Logger logger = LogManager.getLogger(LockMethodInterceptor.class);
	
	@Autowired
    RedisLockHol redisLockHol;
	
    @Around("execution(* com.tempus.gss.product.hol.service..*(..)) && @annotation(com.tempus.gss.product.hol.utils.CacheHolLock)")
    public Object interceptor(ProceedingJoinPoint pjp) throws RuntimeException{
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        CacheHolLock lock = method.getAnnotation(CacheHolLock.class);
        if (StringUtils.isEmpty(lock.prefix())) {
            throw new RuntimeException("lock key don't null...");
        }
        String lockKey = "CacheHolLock";
		try {
			lockKey = LockKeyGenerator.getLockKey(pjp);
		} catch (Exception e) {
			throw new RuntimeException("获取参数异常");
		}
        String value = UUID.randomUUID().toString();
        try {
            // 假设上锁成功，但是设置过期时间失效，以后拿到的都是 false
            final boolean success = redisLockHol.lock(lockKey, value, lock.expire(), lock.timeUnit());
            if (!success) {
            	logger.info("推送酒店订单状态变更为重复提交, 重复提交参数为: "+lockKey);
                throw new RuntimeException("重复提交");
            }
            try {
                return pjp.proceed();
            } catch (Throwable throwable) {
            	logger.info("推送酒店订单状态变更为重复提交, 重复提交参数为: "+lockKey);
                throw new RuntimeException(throwable.getMessage());
            }
        }
        finally {
        	redisLockHol.unlock(lockKey, value);
        }
    }
    
    
}
