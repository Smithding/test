package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;
/**
 * 自己的酒店订单状态枚举类
 * @author kai.yang
 *
 */
public enum OwnerOrderStatus implements Serializable {
	/**
	 * 处理中
	 */
	ORDER_ONGOING("100", "处理中"),
	/**
	 * 预定成功
	 */
	BOOK_OK("101","预订成功"),
	/**
	 * 下单失败
	 */
	BOOK_FAIL("102", "下单失败"),
	/**
	 * 已确认
	 */
	ALREADY_CONRIRM("200", "已确认"),
	/**
	 * 新单
	 */
	NEW_ORDER("300", "新单"),
	/**
	 * 变价
	 */
	CHANGE_PRICE("301", "变价"),
	/**
	 * 取消中
	 */
	CANCEL_ONGOING("400", "取消中"),
	/**
	 * 取消成功
	 */
	CANCEL_OK("401","取消成功"),
	/**
	 * 满房
	 */
	FULL_HOTEL("500", "满房"),
	/**
	 * 测试
	 */
	TEST("550", "测试"),
	/**
	 * 拒单
	 */
	REFUSE_ORDER("600", "拒单"),
	/**
	 * 待审核
	 */
	WAIT_CHECK("650", "待审核"),
	/**
	 * 支付成功
	 */
	PAY_OK("666", "支付成功"),
	/**
	 * 支付失败
	 */
	PAY_BAD("999","支付失败"),
	/**
	 * 在入住
	 */
	RESIDE_ONGOING("700", "在入住"),
	/**
	 * 入住正常
	 */
	RESIDE_OK("701", "入住正常"),
	/**
	 * 确认未入住
	 */
	NO_RESIDE("702", "NOSHOW"),
	/**
	 * 提前离店
	 */
	BEFORE_RESIDE("800", "LESSSHOW"),
	/**
	 * 延住
	 */
	AFTER_RESIDE("801", "延住"),
	
	/**
	 * 已结算
	 */
	ALREADY_CLOSE("900", "已结算"),
	/**
	 * 订单完成
	 */
	ORDER_CLOSE("950", "订单完成");
	
	private String key;
	private String value;
	
	private OwnerOrderStatus(String key,String value) {
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
	 public static OwnerOrderStatus keyOf(String index)
	    {
	        for (OwnerOrderStatus key : values())
	        {
	            if (key.getKey().equals(index))
	            {
	                return key;
	            }
	        }
	        return null;
	    }
	
	
	
}
