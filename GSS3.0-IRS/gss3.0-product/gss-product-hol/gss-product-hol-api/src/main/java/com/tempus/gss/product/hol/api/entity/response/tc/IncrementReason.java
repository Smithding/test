package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;
/**
 *  订单状态增量查询接口返回的订单变化原因枚举
 * @author kai.yang
 *
 */
public enum IncrementReason implements Serializable{
	
	
	ORDER_CANCEL(0, "订单取消"),
	
	INVENTORY_CONFIRM(1, "库存确认"),
	
	ORDER_PAY(2,"订单支付"),
	
	ORDER_CONFIRM(3, "订单库存确认"),
	
	ORDER_REFUND(4, "订单申请退款"),
	
	ORDER_REFUND_FINISH(5, "订单确认退款"),
	
	ORDER_CREATE(6, "订单创建");
	
	private Integer key;
	private String value;
	private IncrementReason(Integer key,String value) {
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
	 public static IncrementReason keyOf(int index)
	    {
	        for (IncrementReason key : values())
	        {
	            if (key.getKey()==index)
	            {
	                return key;
	            }
	        }
	        return null;
	    }
	
	
}
