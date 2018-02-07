package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;


public enum CreditCardsTcType implements Serializable{
	
	/**
	 * 2^0：国内发行银联卡
	 */
	ZERO(0, "国内发行银联卡"),
	/**
	 * 2^1：万事达 Master
	 */
	ONE(1, "万事达Master"),
	/**
	 * 2^2：威士VISA
	 */
	TWO(2, "威士VISA"),
	/**
	 * 2^3：运通 AMEX
	 */
	THREE(3, "运通AMEX"),
	/**
	 * 2^4：大莱
	 */
	FOUR(4, "大莱"),
	/**
	 * 2^5 JCB 卡
	 */
	FIVE(5, "JCB卡");
	
	private Integer key;
	private String value;
	private CreditCardsTcType(Integer key,String value) {
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
	 public static CreditCardsTcType keyOf(int index)
	    {
	        for (CreditCardsTcType key : values())
	        {
	            if (key.getKey()==index)
	            {
	                return key;
	            }
	        }
	        return null;
	    }
	
}
