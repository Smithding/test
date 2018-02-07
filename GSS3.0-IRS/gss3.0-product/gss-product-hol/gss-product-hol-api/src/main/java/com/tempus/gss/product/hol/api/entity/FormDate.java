package com.tempus.gss.product.hol.api.entity;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;


public class FormDate implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JSONField(name = "Day")
	private String day;
	
	@JSONField(name = "Price")
	private Integer price;
	
	/*@JSONField(name = "Day2")
	private String day2;*/
	
	@JSONField(name = "Price2")
	private Integer price2;

	
	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
	
	/*public String getDay2() {
		return day2;
	}

	public void setDay2(String day2) {
		this.day2 = day2;
	}*/

	public Integer getPrice2() {
		return price2;
	}

	public void setPrice2(Integer price2) {
		this.price2 = price2;
	}
	
	
}
