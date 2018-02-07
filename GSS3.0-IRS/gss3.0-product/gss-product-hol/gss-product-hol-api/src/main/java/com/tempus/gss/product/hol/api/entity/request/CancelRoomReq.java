package com.tempus.gss.product.hol.api.entity.request;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class CancelRoomReq extends BaseRequest implements Serializable  {

	private static final long serialVersionUID = 1L;

	/**
	 * 订单编号
	 */
	@JSONField(name = "OrderNo")
	private String orderNo;
	/**
	 * 用户编号
	 */
	@JSONField(name = "UserID")
	private String userID;

	/**
	 * 用户名称
	 */
	@JSONField(name = "UserName")
	private String userName;
	/**
	 * 备注
	 */
	@JSONField(name = "Remark")
	private String remark;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
