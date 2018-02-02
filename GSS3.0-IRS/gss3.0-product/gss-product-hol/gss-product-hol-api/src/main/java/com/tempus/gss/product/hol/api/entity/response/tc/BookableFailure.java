package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * 可订检查失败原因
 * @author kai.yang
 *
 */
public class BookableFailure implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 失败原因
	 */
	@JSONField(name = "Reason")
	private Long reason;
	/**
	 * 失败原因文本描述
	 */
	@JSONField(name = "Description")
	private String description;
	public Long getReason() {
		return reason;
	}
	public void setReason(Long reason) {
		this.reason = reason;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
