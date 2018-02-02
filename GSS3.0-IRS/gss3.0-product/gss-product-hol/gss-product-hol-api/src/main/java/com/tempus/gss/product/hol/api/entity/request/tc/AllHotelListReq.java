package com.tempus.gss.product.hol.api.entity.request.tc;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * 全量同步TC酒店列表入参
 * @author kai.yang
 *
 */
public class AllHotelListReq implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 酒店ID
	 */
	@JSONField(name = "ResId")
	private Long resId;
	/**
	 * 酒店名称（支持模糊查询）
	 */
	@JSONField(name = "ResName")
	private String resName;
	/**
	 * 房型名称（支持模糊查询）
	 */
	@JSONField(name = "ResProName")
	private String resProName;
	/**
	 * 省份名称（去除“省”字样，例如：“江苏”）（支持模糊查询）
	 */
	@JSONField(name = "ProvinceName")
	private String provinceName; 
	/**
	 * 城市名称（去除“市”字样，例如：“苏州”）（支持模糊查询）
	 */
	@JSONField(name = "CityName")
	private String cityName;
	/**
	 * 每页条数
	 */
	@JSONField(name = "PageSize")
	private Integer pageSize;
	/**
	 *（页面）当前页码（最大 2000）
	 */
	@JSONField(name = "PageIndex")
	private Integer pageIndex;
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
	public String getResProName() {
		return resProName;
	}
	public void setResProName(String resProName) {
		this.resProName = resProName;
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
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	
	
	
}
