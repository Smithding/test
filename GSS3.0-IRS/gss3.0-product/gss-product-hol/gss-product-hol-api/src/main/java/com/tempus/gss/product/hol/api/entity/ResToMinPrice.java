package com.tempus.gss.product.hol.api.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class ResToMinPrice implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private Long bqyId;
	
	private Long tcId;
	
	private Long tyId;
	
	private BigDecimal minPrice;
	
	private String noPriceStatus;	//无最低价(1.TC; 2.BQY; 3.TY; 多个酒店无价格组合,例如:同程和八千翼都无最低价值为:12;)
	
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

	public BigDecimal getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(BigDecimal minPrice) {
		this.minPrice = minPrice;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getNoPriceStatus() {
		return noPriceStatus;
	}

	public void setNoPriceStatus(String noPriceStatus) {
		this.noPriceStatus = noPriceStatus;
	}
	
	
}
