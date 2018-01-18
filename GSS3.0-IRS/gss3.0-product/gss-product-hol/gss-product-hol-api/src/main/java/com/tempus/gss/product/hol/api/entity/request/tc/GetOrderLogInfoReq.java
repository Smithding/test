package com.tempus.gss.product.hol.api.entity.request.tc;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * 获取订单日志信息入参
 * @author kai.yang
 *
 */
public class GetOrderLogInfoReq implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 客户订单号
	 */
	@JSONField(name = "CustomerSerialId")
	private String customerSerialId;
	/**
	 * 操作类型
	 */
	@JSONField(name = "OperateType")
	private Integer operateType;
	public String getCustomerSerialId() {
		return customerSerialId;
	}
	public void setCustomerSerialId(String customerSerialId) {
		this.customerSerialId = customerSerialId;
	}
	public Integer getOperateType() {
		return operateType;
	}
	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}
	
	
	
	
}
