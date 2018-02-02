package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 商圈信息集合
 * @author kai.yang
 *
 */
public class BusinessSection implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 商圈名称
	 */
	@JSONField(name = "BusinessSectionName")
	private String businessSectionName;
	/**
	 * 商圈全拼
	 */
	@JSONField(name = "BSFullSpelling")
	private String BSFullSpelling;
	/**
	 * 商圈首字母
	 */
	@JSONField(name = "BSFirstLetter")
	private String BSFirstLetter;
	/**
	 * 商圈 ID
	 */
	@JSONField(name = "BusinessSectionId")
	private Integer businessSectionId;
	public String getBusinessSectionName() {
		return businessSectionName;
	}
	public void setBusinessSectionName(String businessSectionName) {
		this.businessSectionName = businessSectionName;
	}
	
	
	
	public String getBSFullSpelling() {
		return BSFullSpelling;
	}
	public void setBSFullSpelling(String bSFullSpelling) {
		this.BSFullSpelling = bSFullSpelling;
	}
	public String getBSFirstLetter() {
		return BSFirstLetter;
	}
	public void setBSFirstLetter(String bSFirstLetter) {
		this.BSFirstLetter = bSFirstLetter;
	}
	public Integer getBusinessSectionId() {
		return businessSectionId;
	}
	public void setBusinessSectionId(Integer businessSectionId) {
		this.businessSectionId = businessSectionId;
	}
	
}
