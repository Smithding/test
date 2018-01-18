package com.tempus.gss.product.hol.api.entity;

import java.io.Serializable;

public class HotelName implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String city;
	private Long checkTimes;
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
	
}
