package com.tempus.gss.product.hol.api.entity;

import java.io.Serializable;

public class ResToMinPrice implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private Long bqyId;
	
	private Long tcId;
	
	private Long tyId;
	
	private Integer minPrice;
	
	private String updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getBqyId() {
		return bqyId;
	}

	public void setBqyId(Long bqyId) {
		this.bqyId = bqyId;
	}

	public Long getTcId() {
		return tcId;
	}

	public void setTcId(Long tcId) {
		this.tcId = tcId;
	}

	public Long getTyId() {
		return tyId;
	}

	public void setTyId(Long tyId) {
		this.tyId = tyId;
	}

	public Integer getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Integer minPrice) {
		this.minPrice = minPrice;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
