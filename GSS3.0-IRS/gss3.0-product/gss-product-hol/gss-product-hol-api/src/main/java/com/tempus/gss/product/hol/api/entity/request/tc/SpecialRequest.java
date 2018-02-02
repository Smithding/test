package com.tempus.gss.product.hol.api.entity.request.tc;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * 创建订单时客人特殊要求
 * @author kai.yang
 *
 */
public class SpecialRequest implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 特殊要求代码
	 */
	@JSONField(name = "RequestCode")
	private String requestCode;
	/**
	 * 特殊要求文本
	 */
	@JSONField(name = "RequestText")
	private String requestText;
	/**
	 * 特殊要求名称
	 */
	@JSONField(name = "RequestName")
	private String requestName;
	
	public String getRequestCode() {
		return requestCode;
	}
	public void setRequestCode(String requestCode) {
		this.requestCode = requestCode;
	}
	public String getRequestText() {
		return requestText;
	}
	public void setRequestText(String requestText) {
		this.requestText = requestText;
	}
	public String getRequestName() {
		return requestName;
	}
	public void setRequestName(String requestName) {
		
		this.requestName = requestName;
	}
	
}
