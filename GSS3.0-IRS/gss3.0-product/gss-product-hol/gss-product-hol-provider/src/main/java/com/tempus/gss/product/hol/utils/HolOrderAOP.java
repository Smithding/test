package com.tempus.gss.product.hol.utils;

import java.lang.reflect.Method;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.tempus.gss.bbp.util.StringUtil;
import com.tempus.gss.util.JsonUtil;

@Aspect
@Component
public class HolOrderAOP {
	protected final static Logger log = LoggerFactory.getLogger(HolOrderAOP.class);
	
	 @Pointcut("@annotation(com.tempus.gss.product.hol.utils.HolOrderAnnotation)")
	 //@Pointcut("execution(* com.tempus.gss.product.hol.service..*(..)) and @annotation(com.tempus.gss.product.hol.utils.HolOrderAnnotation)")
	 private void pointCutMethod(){}
	 
	 @Before("pointCutMethod()")
	 public void repeatOrderPush(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		LocalTime localTime = LocalTime.now();
		int minuteOfHour = localTime.getMinuteOfHour();
		int second = localTime.getSecondOfMinute();
		
		System.out.println("拦截的内容: "+JsonUtil.toJson(args)+", 分: "+minuteOfHour+", 秒: "+second);
	 }
	 
	 private List<String> getHolInfo(JoinPoint joinPoint) throws ClassNotFoundException {
	        List<String> list = Lists.newArrayList();
	        String methodName = joinPoint.getSignature().getName();
	        String targetName = joinPoint.getTarget().getClass().getName();
	        Class targetClass = Class.forName(targetName);
	        Method[] methods = targetClass.getMethods();
	        for (Method method : methods){
	            if(method.getName().equals(methodName)){
	            	HolOrderAnnotation holAnnotation = method.getAnnotation(HolOrderAnnotation.class);
	            	list.add(holAnnotation.action());
	            	list.add(holAnnotation.targetType());
	            	Object[] args = joinPoint.getArgs();
	            	System.out.println("拦截的内容args: "+JsonUtil.toJson(args));
	            }
	        }
	        return list;
	    }

	 
}
