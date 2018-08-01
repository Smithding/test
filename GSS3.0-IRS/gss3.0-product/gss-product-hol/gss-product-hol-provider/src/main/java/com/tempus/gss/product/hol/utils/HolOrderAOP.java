package com.tempus.gss.product.hol.utils;

import java.lang.reflect.Method;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
	
	 @Pointcut("@annotation(com.tempus.gss.product.hol.utils.HolAnnotation)")
	 //@Pointcut("execution(* com.tempus.gss.product.hol.service..*(..)) and @annotation(com.tempus.gss.product.hol.utils.HolAnnotation)")
	 private void pointCutMethod(){}
	 
	 @Before("pointCutMethod()")
	 public void repeatOrderPush(JoinPoint joinPoint, HolAnnotation holAnnotation) {
		Object[] args = joinPoint.getArgs();
		String aopTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		//LocalTime localTime = LocalTime.now();
		//int minuteOfHour = localTime.getMinuteOfHour();
		//int second = localTime.getSecondOfMinute();
		
		System.out.println("拦截方法名: "+holAnnotation.remark()+" ,拦截的内容: "+JsonUtil.toJson(args)+", 拦截时间: "+aopTime);
	 }
	 
	 private List<String> getHolInfo(JoinPoint joinPoint) throws ClassNotFoundException {
	        List<String> list = Lists.newArrayList();
	        String methodName = joinPoint.getSignature().getName();
	        String targetName = joinPoint.getTarget().getClass().getName();
	        Class targetClass = Class.forName(targetName);
	        Method[] methods = targetClass.getMethods();
	        for (Method method : methods){
	            if(method.getName().equals(methodName)){
	            	HolAnnotation holAnnotation = method.getAnnotation(HolAnnotation.class);
	            	list.add(holAnnotation.action());
	            	list.add(holAnnotation.targetType());
	            	Object[] args = joinPoint.getArgs();
	            	System.out.println("拦截的内容args: "+JsonUtil.toJson(args));
	            }
	        }
	        return list;
	    }

	 
}
