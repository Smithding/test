package com.tempus.gss.product.hol.api.entity.request.tc;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * 支付前取消订单入参
 * @author kai.yang
 *
 */
public class CancelOrderBeforePayReq implements Serializable{
	private static final long serialVersionUID= 1L;
	/**
	 * 客户订单号
	 */
	@JSONField(name = "OrderId")
	private String orderId;
	/**
	 * 取消人姓名
	 */
	@JSONField(name = "UserName")
	private String userName;
	/**
	 * 取消原因id
	 */
	@JSONField(name = "ReasonId")
	private Integer reasonId;
	/**
	 * 其他原因
	 */
	@JSONField(name = "OtherReason")
	private String otherReason;
	
	private String supplierNo;		//供应商编号
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getReasonId() {
		return reasonId;
	}
	public void setReasonId(Integer reasonId) {
		this.reasonId = reasonId;
	}
	public String getOtherReason() {
		return otherReason;
	}
	public void setOtherReason(String otherReason) {
		this.otherReason = otherReason;
	}
	public String getSupplierNo() {
		return supplierNo;
	}
	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}
	
}
