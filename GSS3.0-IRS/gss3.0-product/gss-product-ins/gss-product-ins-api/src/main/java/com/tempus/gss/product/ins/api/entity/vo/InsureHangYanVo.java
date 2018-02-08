package com.tempus.gss.product.ins.api.entity.vo;

/**
 * Created by Administrator on 2016/10/25.
 */
public class InsureHangYanVo {
	/*
	* 航班号
	* */
	private String flightNo;

	/*
	*航班日期 yyyyMMddHHmmss
	* */
	private String flightDate;

	/*
	*目的地城市三字码
	* */
	private String destinationCode;

	/*
	* 出发城市三字码
	* */
	private String originCode;

	/*
	* 出发城市
	* */
	private String originCity;

	/*
	* 目的地城市
	* */
	private String destinationCity;

	public String getFlightNo() {

		return flightNo;
	}

	public void setFlightNo(String flightNo) {

		this.flightNo = flightNo;
	}

	public String getFlightDate() {

		return flightDate;
	}

	public void setFlightDate(String flightDate) {

		this.flightDate = flightDate;
	}

	public String getDestinationCode() {

		return destinationCode;
	}

	public void setDestinationCode(String destinationCode) {

		this.destinationCode = destinationCode;
	}

	public String getOriginCode() {

		return originCode;
	}

	public void setOriginCode(String originCode) {

		this.originCode = originCode;
	}

	public String getOriginCity() {

		return originCity;
	}

	public void setOriginCity(String originCity) {

		this.originCity = originCity;
	}

	public String getDestinationCity() {

		return destinationCity;
	}

	public void setDestinationCity(String destinationCity) {

		this.destinationCity = destinationCity;
	}
}
