package com.tempus.gss.product.tra.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class PropertiesUtils {
	
	private static Properties PRO=new Properties();  
	
	private static Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);
	
	//机票配置信息
	private final static Map<String, String> airMap = new HashMap<String, String>();
	
	/**
	 * 读取机票配置
	 * @return
	 */
	public static Map<String, String> readAirProperties(){
		Resource resource = new ClassPathResource("/train.properties");
		try {
			Properties props = PropertiesLoaderUtils.loadProperties(resource);
			Set<Entry<Object, Object>> set = props.entrySet();
			Iterator<Entry<Object, Object>> iterator = set.iterator();
			while (iterator.hasNext()) {
				Entry<Object, Object> o = iterator.next();
				airMap.put((String)o.getKey(), (String)o.getValue());
			}
		} catch (IOException e) {
			e.printStackTrace();
			LoggerFactory.getLogger("log.error").error("加载air.properties文件异常,",e);
		}
		
		return airMap;
	}
	
	public static String get(String key)  {
		if(key==null||"".equals(key)){
			throw  new IllegalArgumentException("Properties key can't allow empty!");
		}
		try {
			logger.info("加载 application.properties");
			PRO.load(PropertiesUtils.class.getClassLoader().getResourceAsStream("train.properties"));
			logger.info("内容是="+PRO.toString());	
		} catch (IOException e) {				
			logger.error("air.properties 读取错误!",e);				
		} 
		String result  = PRO.getProperty(key); 
		return result;
	}
}
