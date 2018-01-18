package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class CancelOrderRes implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 取消返回结果
	 */
	@JSONField(name = "Result")
	private Boolean result;
	/**
	 * 最晚取消时间
	 */
	@JSONField(name = "LasestCancelTime")
	private String lasestCancelTime;
	/**
	 * 取消结果说明
	 */
	@JSONField(name = "Msg")
	private String msg;
	
	public Boolean getResult() {
		return result;
	}
	public void setResult(Boolean result) {
		this.result = result;
	}
	public String getLasestCancelTime() {
		return lasestCancelTime;
	}
	public void setLasestCancelTime(String lasestCancelTime) {
		this.lasestCancelTime = lasestCancelTime;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
