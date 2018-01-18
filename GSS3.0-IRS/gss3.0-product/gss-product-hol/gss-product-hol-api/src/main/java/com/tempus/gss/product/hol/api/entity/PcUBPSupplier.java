package com.tempus.gss.product.hol.api.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.alibaba.fastjson.annotation.JSONField;
import com.tempus.gss.product.hol.api.entity.request.BaseRequest;

public class PcUBPSupplier extends BaseRequest implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JSONField(name = "UBPCode")
	private String UBPCode;
	
	@JSONField(name = "Rate")
	private BigDecimal rate;
	
	@JSONField(name = "Context")
	private String context;
	
	@JSONField(name = "SupplierCode")
	private String supplierCode;
	
	@JSONField(name = "PayType")
	private String payType;

	public String getUBPCode() {
		return UBPCode;
	}

	public void setUBPCode(String uBPCode) {
		UBPCode = uBPCode;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
}
