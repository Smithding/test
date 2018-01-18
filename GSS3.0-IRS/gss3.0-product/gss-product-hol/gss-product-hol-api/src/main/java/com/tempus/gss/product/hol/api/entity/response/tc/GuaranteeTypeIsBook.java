package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;
/**
 * 担保信息分类的枚举类
 * @author kai.yang
 *
 */
public enum GuaranteeTypeIsBook implements Serializable{
	
	/**
	 * 0-不担保
	 */
	ZERO("0", "不担保"),
	/**
	 * 1-峰时担保
	 */
	ONE("1", "峰时担保"),
	/**
	 * 2-全额担保
	 */
	TWO("2", "全额担保"),
	/**
	 * 4-超时担保
	 */
	THREE("3", "超时担保"),
	/**
	 * 4-一律担保
	 */
	FOUR("4", "一律担保"),
	/**
	 * 5-手机担保(不担保,要有手机号)
	 */
	FIVE("5", "手机担保");
	
	private String key;
	private String value;
	private GuaranteeTypeIsBook(String key,String value) {
        this.key=key;
        this.value=value;
    }
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	 public static GuaranteeTypeIsBook keyOf(int index)
	    {
	        for (GuaranteeTypeIsBook key : values())
	        {
	            if (key.getKey().equals(String.valueOf(index)))
	            {
	                return key;
	            }
	        }
	        return null;
	    }

}
