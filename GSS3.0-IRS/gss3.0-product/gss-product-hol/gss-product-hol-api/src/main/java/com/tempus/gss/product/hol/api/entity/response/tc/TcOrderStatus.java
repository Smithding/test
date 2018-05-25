package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;

/**
 * 同程订单状态枚举类
 * @author kai.yang
 *
 */
public enum TcOrderStatus implements Serializable{
	/**
	 * 默认状态
	 */
	ORIGINAL("-1","默认状态"),
	/**
	 * 待确认库存
	 */
	WATI_CONFIRM_INVENTORY("0","待确认库存"),
	/**
	 * 待支付
	 */
	WAIT_PAY("1","待支付"),
	/**
	 * 已取消
	 */
	ALREADY_CANCEL("2","已取消"),
	/**
	 * 已支付
	 */
	ALREADY_PAY("3","已支付"),
	/**
	 * 待同程确认
	 */
	WAIT_TC_CONFIRM("5","待同程确认"),
	/**
	 * 同程已确认
	 */
	ALREADY_TC_CONFIRM("10","同程已确认"),
	/**
	 * 确认入住
	 */
	CONFIRM_TO_ROOM("11","确认入住"),
	/**
	 * 确认未住
	 */
	CONFIRM_NOT_TO_ROOM("12","确认未住"),
	/**
	 * 申请部分退款(有人出游)
	 */
	APPLY_PART_REFUND("15","申请部分退款(有人出游)"),
	/**
	 * 申请全额退款
	 */
	APPLY_ALL_REFUND("20","申请全额退款"),
	/**
	 * 全额退款结束
	 */
	FINISH_ALL_REFUND("25","全额退款结束"),
	/**
	 * 部分退款结束
	 */
	FINISH_PART_REFUND("30","部分退款结束"),
	/**
	 * 已结算
	 */
	ALREADY_ACCOUNT("35","已结算"),
	/**
	 * 订单结束
	 */
	ORDER_FINISH("40","订单结束"),
	/**
	 * 新单
	 */
	NEW_ORDER("45","新单"),
	/**
	 * 订单变更
	 */
	ORDER_CHANGED("50","订单变化");
	
	private String key;
	private String value;
	
	private TcOrderStatus(String key,String value) {
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
	 public static TcOrderStatus keyOf(String index)
	    {
	        for (TcOrderStatus key : values())
	        {
	            if (key.getKey().equals(index))
	            {
	                return key;
	            }
	        }
	        return null;
	    }
	
}
