package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * 分销商日志内容
 */
public class OrderLogModel implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 客户订单号
	 */
	@JSONField(name = "CustomerSerialId")
	private String customerSerialId;
	/**
	 * 操作人 id
	 */
	@JSONField(name = "OperatorId")
	private String operatorId;
	/**
	 * 操作类型 （0：下单
	 * 1：更改订单状态
	 * 2：更改订单金额 
	 * 3：更改授信 
	 * 5：赔款
	 * 6：退款 
	 * 7：修改外部订单号 
	 * 8：变更 
	 * 9：确认入住 
	 * 10：订单取消 
	 * 11：确认未住）
	 */
	@JSONField(name = "OperateType")
	private Integer operateType;
	/**
	 * 操作内容
	 */
	@JSONField(name = "OperateContent")
	private String operateContent;
	/**
	 * 操作人姓名
	 */
	@JSONField(name = "OperatorName")
	private String operatorName;
	/**
	 * 操作时间
	 */
	@JSONField(name = "OperateTime")
	private String operateTime;
	public String getCustomerSerialId() {
		return customerSerialId;
	}
	public void setCustomerSerialId(String customerSerialId) {
		this.customerSerialId = customerSerialId;
	}
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	public Integer getOperateType() {
		return operateType;
	}
	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}
	public String getOperateContent() {
		return operateContent;
	}
	public void setOperateContent(String operateContent) {
		this.operateContent = operateContent;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
}
