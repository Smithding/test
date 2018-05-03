package com.tempus.gss.product.hol.api.entity.vo.bqy.request;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 查询酒店ID
 */
public class QueryHotelIdParam extends BaseQueryParam implements Serializable {

	private static final long serialVersionUID = 1L;

	@JSONField(name="CityId")
	private String cityId;			//城市Id
	
	@JSONField(name="PageNo")
	private int pageNo;				//当前页数
	
	@JSONField(name="PageSize")
	private int pageSize;			//行数

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
}
