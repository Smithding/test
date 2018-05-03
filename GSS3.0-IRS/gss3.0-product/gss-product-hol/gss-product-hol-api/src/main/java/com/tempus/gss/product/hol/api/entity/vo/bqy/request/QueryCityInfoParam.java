package com.tempus.gss.product.hol.api.entity.vo.bqy.request;

import java.io.Serializable;

/**
 * 城市信息
 */
public class QueryCityInfoParam extends BaseQueryParam implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String CityName;  //城市名称

	public String getCityName() {
		return CityName;
	}

	public void setCityName(String cityName) {
		CityName = cityName;
	}
	
}
