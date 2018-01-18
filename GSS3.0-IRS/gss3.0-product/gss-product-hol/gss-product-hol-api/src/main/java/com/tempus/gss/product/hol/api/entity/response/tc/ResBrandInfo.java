package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * TC品牌信息
 * @author kai.yang
 *
 */
public class ResBrandInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 品牌名称
	 */
	@JSONField(name = "ResBrandName")
	private String resBrandName;
	/**
	 * 品牌商标
	 */
	@JSONField(name = "ResLogo")
	private String resLogo;
	/**
	 * 品牌类型
	 */
	@JSONField(name = "ResBrandType")
	private Integer resBrandType;
	/**
	 * 品牌 Id
	 */
	@JSONField(name = "Id")
	private Long id;
	public String getResBrandName() {
		return resBrandName;
	}
	public void setResBrandName(String resBrandName) {
		this.resBrandName = resBrandName;
	}
	public String getResLogo() {
		return resLogo;
	}
	public void setResLogo(String resLogo) {
		this.resLogo = resLogo;
	}
	public Integer getResBrandType() {
		return resBrandType;
	}
	public void setResBrandType(Integer resBrandType) {
		this.resBrandType = resBrandType;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	
	
	
	
	
}
