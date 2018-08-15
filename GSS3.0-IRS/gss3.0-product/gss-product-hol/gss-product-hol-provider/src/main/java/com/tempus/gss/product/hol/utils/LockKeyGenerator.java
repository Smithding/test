package com.tempus.gss.product.hol.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Objects;

import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;


/**
 * Key生成实现
 * 解析带 CacheLock 注解的属性，获取对应的属性值，生成一个全新的缓存 Key
 * @author Cassini
 *
 */
@Component
public class LockKeyGenerator{
	
	public static String getLockKey(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        CacheHolLock lockAnnotation = method.getAnnotation(CacheHolLock.class);
        final Object[] args = pjp.getArgs();
        final Parameter[] parameters = method.getParameters();
        
        StringBuilder builder = new StringBuilder();
        // TODO 默认解析方法里面带 CacheParam 注解的属性,如果没有尝试着解析实体对象中的
        for (int i = 0; i < parameters.length; i++) {
            final CacheParam annotation = parameters[i].getAnnotation(CacheParam.class);
            if (annotation == null) {
                continue;
            }
            builder.append(lockAnnotation.delimiter()).append(args[i]);
        }
        if (StringUtils.isEmpty(builder.toString())) {
            for (int i = 0; i < args.length; i++) {
                final Object object = args[i];
                final Field[] fields = object.getClass().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    Object field2 = ReflectionUtils.getField(field, object);
                    if(!Objects.isNull(field2)) {
                    	builder.append(lockAnnotation.delimiter()).append(field2);
                    }
                }
            }
        }
        //System.out.println("key: "+(lockAnnotation.prefix() + builder.toString()));
        return lockAnnotation.prefix() + builder.toString();
	}
	
	

}
