package com.tempus.gss.product.tra.api.entity;

import java.io.Serializable;

public class QueryCheciRequest  implements Serializable {

	private String checi;//车次号
	
	private String date;//yyyy-MM-dd

	public String getCheci() {
		return checi;
	}

	public void setCheci(String checi) {
		this.checi = checi;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
}
