package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * 酒店id集合
 * @author kai.yang
 *
 */
public class ResInfoList implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 酒店 Id
	 */
	@JSONField(name = "ResId")
	private Long resId;
	/**
	 * 酒店名称
	 */
	@JSONField(name = "ResName")
	private String resName;
	/**
	 * 省份名称（去除“省”字样，例如：“江苏”）
	 */
	@JSONField(name = "ProvinceName")
	private String provinceName;
	/**
	 * 城市名称（去除“市”字样，例如：“苏州”）
	 */
	@JSONField(name = "CityName")
	private String cityName;
	/**
	 * 酒店房型集合【已废弃】
	 */
	@JSONField(name = "ResourceProductInfos")
	private List<ResourceProductInfo> resourceProductInfos;
	public Long getResId() {
		return resId;
	}
	public void setResId(Long resId) {
		this.resId = resId;
	}
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public List<ResourceProductInfo> getResourceProductInfos() {
		return resourceProductInfos;
	}
	public void setResourceProductInfos(List<ResourceProductInfo> resourceProductInfos) {
		this.resourceProductInfos = resourceProductInfos;
	}
	
	
	
}
