package com.tempus.gss.product.hol.api.entity;

import java.io.Serializable;

public class ResNameSum implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 酒店id
	 */
	private Long resId;
	
	/**
	 * 酒店名称
	 */
	private String resName;
	
	/**
	 * 供应商编号
	 */
	private String supplierNo;
	
	
	/**
	 * 1: TC, 2: BQY, 3: TY, 0: All, 
	 */
	private Integer resType;


	public Long getResId() {
		return resId;
	}


	public void setResId(Long resId) {
		this.resId = resId;
	}


	public String getResName() {
		return resName;
	}


	public void setResName(String resName) {
		this.resName = resName;
	}


	public String getSupplierNo() {
		return supplierNo;
	}


	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}


	public Integer getResType() {
		return resType;
	}


	public void setResType(Integer resType) {
		this.resType = resType;
	}
	
}
