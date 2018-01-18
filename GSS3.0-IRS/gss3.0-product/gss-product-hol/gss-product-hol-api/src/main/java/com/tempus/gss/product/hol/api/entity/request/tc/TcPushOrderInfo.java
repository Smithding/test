package com.tempus.gss.product.hol.api.entity.request.tc;

import java.io.Serializable;
import java.math.BigDecimal;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * 同程推送订单状态入参
 * @author kai.yang
 *
 */
public class TcPushOrderInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 操作类型
	 */
	@JSONField(name = "OperateType")
	private String operateType;
	/**
	 * 客户订单号
	 */
	@JSONField(name = "OrderId")
	private String orderId;
	/**
	 * 订单金额（当OperateType=Refund时OrderAmount为当前退款金额）
	 */
	@JSONField(name = "OrderAmount")
	private BigDecimal orderAmount;
	/**
	 * 历史退款金额（包括本次退款金额）
	 */
	@JSONField(name = "AllRefundAmount")
	private BigDecimal allRefundAmount;
	/**
	 * 退款金额
	 */
	@JSONField(name = "RefundAmount")
	private BigDecimal refundAmount;
	/**
	 * 退款单号
	 */
	@JSONField(name = "RefundNumber")
	private String refundNumber;
	/**
	 * 同程收取服务费
	 */
	@JSONField(name = "ServiceCharge")
	private BigDecimal serviceCharge;
	/**
	 * 退款原因备注
	 */
	@JSONField(name = "Remark")
	private String remark;
	/**
	 * 外部订单号
	 */
	@JSONField(name = "ExternalOrderId")
	private String externalOrderId;
	/**
	 * 订单有效性
	 */
	@JSONField(name = "OrderFlag")
	private Boolean orderFlag;
	/**
	 * 补单通知的新订单号
	 */
	@JSONField(name = "NewOrderId")
	private String newOrderId;
	
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public BigDecimal getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}
	public BigDecimal getAllRefundAmount() {
		return allRefundAmount;
	}
	public void setAllRefundAmount(BigDecimal allRefundAmount) {
		this.allRefundAmount = allRefundAmount;
	}
	public BigDecimal getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}
	public String getRefundNumber() {
		return refundNumber;
	}
	public void setRefundNumber(String refundNumber) {
		this.refundNumber = refundNumber;
	}
	public BigDecimal getServiceCharge() {
		return serviceCharge;
	}
	public void setServiceCharge(BigDecimal serviceCharge) {
		this.serviceCharge = serviceCharge;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getExternalOrderId() {
		return externalOrderId;
	}
	public void setExternalOrderId(String externalOrderId) {
		this.externalOrderId = externalOrderId;
	}
	public Boolean getOrderFlag() {
		return orderFlag;
	}
	public void setOrderFlag(Boolean orderFlag) {
		this.orderFlag = orderFlag;
	}
	public String getNewOrderId() {
		return newOrderId;
	}
	public void setNewOrderId(String newOrderId) {
		this.newOrderId = newOrderId;
	}
}
