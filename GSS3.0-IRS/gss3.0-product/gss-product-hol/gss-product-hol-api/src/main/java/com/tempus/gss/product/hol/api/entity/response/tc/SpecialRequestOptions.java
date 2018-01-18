package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * 可定检查时返回的特殊备注列表
 * @author kai.yang
 *
 */
public class SpecialRequestOptions implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 特殊要求名称
	 */
	@JSONField(name = "Name")
	private String name;
	/**
	 * 特殊要求代码
	 */
	@JSONField(name = "Code")
	private String code;
	/**
	 * 特殊要求文本
	 */
	@JSONField(name = "CodeContext")
	private String codeContext;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCodeContext() {
		return codeContext;
	}
	public void setCodeContext(String codeContext) {
		this.codeContext = codeContext;
	}
	

}
