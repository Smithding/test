package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public class BlackOrderExtVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	/**
	 * 核价实体
	 */
	private SaleOrderExtVo saleOrderExtVo;
	
	
	/**
	 * 供应商编号
	 */
	private Long supplierNo;
	
	
	/**
	 * 出票航司
	 */
	private String airLine;
	
	private PassengerListVo pgerVoList;


	public SaleOrderExtVo getSaleOrderExtVo() {
		return saleOrderExtVo;
	}


	public void setSaleOrderExtVo(SaleOrderExtVo saleOrderExtVo) {
		this.saleOrderExtVo = saleOrderExtVo;
	}


	public Long getSupplierNo() {
		return supplierNo;
	}


	public void setSupplierNo(Long supplierNo) {
		this.supplierNo = supplierNo;
	}


	public String getAirLine() {
		return airLine;
	}


	public void setAirLine(String airLine) {
		this.airLine = airLine;
	}


	public PassengerListVo getPgerVoList() {
		return pgerVoList;
	}


	public void setPgerVoList(PassengerListVo pgerVoList) {
		this.pgerVoList = pgerVoList;
	}


	@Override
	public String toString() {
		return "BlackOrderExtVo [saleOrderExtVo=" + saleOrderExtVo + ", supplierNo=" + supplierNo + ", airLine="
				+ airLine + ", pgerVoList=" + pgerVoList + "]";
	}

	
}