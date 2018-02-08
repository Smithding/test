package com.tempus.gss.product.hol.api.util;

import java.io.*;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;

public class PropertyUtil {
	
    private static Properties props;
    static{
        loadProps();
    }

    synchronized static private void loadProps(){
    	
        props = new Properties();
        InputStream in = null;
        try {
        	/*String property = System.getProperty("spring.profiles.active");
        	System.out.println("环境: "+property);*/
            in = PropertyUtil.class.getClassLoader().getResourceAsStream("application-dev.properties");
            //in = PropertyUtil.class.getResourceAsStream("/jdbc.properties");
            props.load(in);
        } catch (FileNotFoundException e) {
           // logger.error("jdbc.properties文件未找到");
        } catch (IOException e) {
          //  logger.error("出现IOException");
        } finally {
            try {
                if(null != in) {
                    in.close();
                }
            } catch (IOException e) {
               // logger.error("jdbc.properties文件流关闭出现异常");
            }
        }
       // logger.info("加载properties文件内容完成...........");
      //  logger.info("properties文件内容：" + props);
    }

    public static String getProperty(String key){
        if(null == props) {
            loadProps();
        }
        return props.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        if(null == props) {
            loadProps();
        }
        return props.getProperty(key, defaultValue);
    }
    
   
    
    
    
    
    
    
    
    
    
    
    
}
