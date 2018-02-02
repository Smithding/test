package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * 取消订单返回信息实体类
 * @author kai.yang
 *
 */
public class CancelTcOrderBeforePay implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 是否取消成功
	 */
	@JSONField(name = "IsSuccessed")
	private Boolean isSuccessed;
	/**
	 * 订单取消提示信息
	 */
	@JSONField(name = "Msg")
	private String msg;
	public Boolean getIsSuccessed() {
		return isSuccessed;
	}
	public void setIsSuccessed(Boolean isSuccessed) {
		this.isSuccessed = isSuccessed;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
