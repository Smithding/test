//package com.tempus.gss.product.unp.api.entity.configs;
//
//import org.springframework.boot.web.servlet.MultipartConfigFactory;
//import org.springframework.cache.annotation.CachingConfigurerSupport;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.servlet.MultipartConfigElement;
//
///**
// * @author ZhangBro
// */
//@Configuration
//public class FileUploadConfig extends CachingConfigurerSupport {
//    @Bean
//    public MultipartConfigElement multipartConfigElement() {
//        MultipartConfigFactory factory = new MultipartConfigFactory();
//        //单个文件最大
//        //KB,MB
//        factory.setMaxFileSize("10240KB");
//        /// 设置总上传数据总大小
//        factory.setMaxRequestSize("102400KB");
//        return factory.createMultipartConfig();
//    }
//}
