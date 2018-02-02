package com.tempus.gss.product.hol.api.entity;

import java.io.Serializable;

public class HotelName implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String city;
	private Long checkTimes;
	/**
	 * 酒店是否可售状态，默认为1可售， 0位不可售，此状态与同程无关
	 */
	private Integer saleStatus;
	private String label;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getCheckTimes() {
		return checkTimes;
	}

	public void setCheckTimes(Long checkTimes) {
		this.checkTimes = checkTimes;
	}

	public Integer getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(Integer saleStatus) {
		this.saleStatus = saleStatus;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
}
