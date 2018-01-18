package com.tempus.gss.product.hol.api.entity.vo;

import java.io.Serializable;

public class EachNightPriceBreak implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String nightPrice;
	
	private String breakFast;

	public String getNightPrice() {
		return nightPrice;
	}

	public void setNightPrice(String nightPrice) {
		this.nightPrice = nightPrice;
	}

	public String getBreakFast() {
		return breakFast;
	}

	public void setBreakFast(String breakFast) {
		this.breakFast = breakFast;
	}
	
}
