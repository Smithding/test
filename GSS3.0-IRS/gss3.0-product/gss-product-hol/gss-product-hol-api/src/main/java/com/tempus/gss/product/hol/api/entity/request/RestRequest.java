package com.tempus.gss.product.hol.api.entity.request;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * rest通用传入参数
 * @author Administrator
 *
 */
public class RestRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 传入参数的json字符串
     */
    @JSONField(name = "RequestJson")
    protected String requestJson;
    /**
     * 操作类型
     */
    @JSONField(name = "Action")
    protected String action;
	public String getRequestJson() {
		return requestJson;
	}
	public void setRequestJson(String requestJson) {
		this.requestJson = requestJson;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
    
    
}
