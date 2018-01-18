package com.tempus.gss.product.tra.util;

public interface SingletonStrategy {
	
	 /**
     * 获取单例对象
     */
    Object instance();

    /**
     * 重置单例
     */
    void reset();

    /**
     * 设置单例
     */
    void setSingletonClassName(String singletonClassName);

}
