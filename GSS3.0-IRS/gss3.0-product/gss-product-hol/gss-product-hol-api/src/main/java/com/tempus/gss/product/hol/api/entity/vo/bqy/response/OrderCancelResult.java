package com.tempus.gss.product.hol.api.entity.vo.bqy.response;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * 订单取消结果
 *
 */
public class OrderCancelResult implements Serializable{

	private static final long serialVersionUID = 1L;

	@JSONField(name="Result")
	private Boolean result;		//是否取消成功
	
	@JSONField(name="Message")
	private String message;		//失败消息

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
