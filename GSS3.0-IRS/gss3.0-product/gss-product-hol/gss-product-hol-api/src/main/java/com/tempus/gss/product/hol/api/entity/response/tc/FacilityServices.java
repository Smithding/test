package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * TC酒店设施
 * @author kai.yang
 *
 */
public class FacilityServices implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 设施名称
	 */
	@JSONField(name = "FacilityServicesName")
	private String facilityServicesName;
	/**
	 * 设施描述
	 */
	@JSONField(name = "Description")
	private String description;
	/**
	 * 设施 Id
	 */
	@JSONField(name = "FacilityServicesId")
	private Integer facilityServicesId;
	/**
	 * 设施类目 id
	 */
	@JSONField(name = "FacilityCategoryId")
	private Integer facilityCategoryId;
	/**
	 * 设施类目名称
	 */
	@JSONField(name = "FacilityCategoryName")
	private String facilityCategoryName;
	/**
	 * 是否是经常使用的设施（0：否；1：是）
	 */
	@JSONField(name = "IsUsual")
	private Byte isUsual;
	public String getFacilityServicesName() {
		return facilityServicesName;
	}
	public void setFacilityServicesName(String facilityServicesName) {
		this.facilityServicesName = facilityServicesName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getFacilityServicesId() {
		return facilityServicesId;
	}
	public void setFacilityServicesId(Integer facilityServicesId) {
		this.facilityServicesId = facilityServicesId;
	}
	public Integer getFacilityCategoryId() {
		return facilityCategoryId;
	}
	public void setFacilityCategoryId(Integer facilityCategoryId) {
		this.facilityCategoryId = facilityCategoryId;
	}
	public String getFacilityCategoryName() {
		return facilityCategoryName;
	}
	public void setFacilityCategoryName(String facilityCategoryName) {
		this.facilityCategoryName = facilityCategoryName;
	}
	public Byte getIsUsual() {
		return isUsual;
	}
	public void setIsUsual(Byte isUsual) {
		this.isUsual = isUsual;
	}
	
	
}
