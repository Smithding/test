package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;
/**
 * 同程酒店星级枚举类
 * @author kai.yang
 *
 */
public enum TCHotelLevel implements Serializable{
	/**
	 * 经济型 32
	 */
	JINGJI(32,"经济型"), 
	/**
	 * 舒适型 31
	 */
	SHUSHI(31,"舒适型"), 
	/**
	 * 高档型 30
	 */
	GAODANG(30,"高档型"),
	/**
	 * 豪华型 28
	 */
	HAOHUA(28,"豪华型"),
	/**
	 * 二星级 27
	 */
	ERXINGJI(27,"二星级"),
	/**
	 * 三星级 26
	 */
	SANXINGJI(26,"三星级"),
	/**
	 * 四星级 24
	 */
	SIXINGJI(24,"四星级"),
	/**
	 * 五星级 23
	 */
	WUXINGJI(23,"五星级"),
	/**
	 * 二星级以下 34
	 */
	DOWNERXINGJI(34,"二星级以下")
	;
	
	private String value;
	private Integer key;
	private TCHotelLevel(Integer key,String value) {
        this.key=key;
        this.value=value;
    }
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Integer getKey() {
		return key;
	}
	public void setKey(Integer key) {
		this.key = key;
	}
	 public static TCHotelLevel keyOf(int index)
	    {
	        for (TCHotelLevel key : values())
	        {
	            if (key.getKey()==index)
	            {
	                return key;
	            }
	        }
	        return null;
	    }
	
}
