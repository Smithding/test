package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;
import java.math.BigDecimal;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * 订单创建返回信息
 * @author kai.yang
 *
 */
public class OrderCreate implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 提示信息
	 */
	@JSONField(name = "Msg")
	private String msg;
	/**
	 * 下单完成后的订单号,下单完成后就可以返回了
	 */
	@JSONField(name = "OrderId")
	private String orderId;
	/**
	 * 下单完成后订单的金额
	 */
	@JSONField(name = "OrderMoney")
	private BigDecimal orderMoney;
	/**
	 * 订单是否可以及时确认,即库存确认
	 */
	@JSONField(name = "IsAffirm")
	private Boolean isAffirm;
	/**
	 * 返回码，0=>下单失败，1=>下单成功，2=>下单成功，支付失败，3=>下单成功，支付成功
	 */
	@JSONField(name = "ResultCode")
	private String resultCode;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public BigDecimal getOrderMoney() {
		return orderMoney;
	}
	public void setOrderMoney(BigDecimal orderMoney) {
		this.orderMoney = orderMoney;
	}
	public Boolean getIsAffirm() {
		return isAffirm;
	}
	public void setIsAffirm(Boolean isAffirm) {
		this.isAffirm = isAffirm;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	
}
