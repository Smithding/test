package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;

/**
 * 订单状态增量查询接口返回的增量类型
 * @author kai.yang
 *
 */
public enum IncrementType implements Serializable{
	/**
	 * 下单变化
	 */
	ORDER_CREATE(0, "下单"),
	/**
	 * 订单状态变化
	 */
	ORDER_FLAG_CHANGE(1, "订单状态变化");
	
	private Integer key;
	private String value;
	private IncrementType(Integer key,String value) {
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
	 public static IncrementType keyOf(int index)
	    {
	        for (IncrementType key : values())
	        {
	            if (key.getKey()==index)
	            {
	                return key;
	            }
	        }
	        return null;
	    }
}
