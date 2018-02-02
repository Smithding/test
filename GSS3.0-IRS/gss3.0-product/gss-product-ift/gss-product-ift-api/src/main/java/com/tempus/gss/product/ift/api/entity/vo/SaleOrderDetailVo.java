package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2016/9/28 0028.
 */
public class SaleOrderDetailVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 名
	 */
	private String name;
	/**
	 * 乘客类型 1：ADT:2：CHD，3：INF
	 */
	private String passengerType;
	/**
	 * 证件类型 PP:护照 NI：身份证 ID:其他证件
	 */
	private String certType;

	/**
	 * 证件编号
	 */
	private String certNo;
	/**
	 * 票号
	 */
	private String ticketNo;
	/**
	 * 航程
	 */
	private String legValue;
	/**
	 * 父航段序号 从01开始
	 */
	private String parentSection;
	/**
	 * 子航段序号 从01开始 保存格式为 复航段序号+子航段序号，即：0101.
	 */
	private String childSection;
	/**
	 * 票价/税费
	 */
	private String fareTax;
	/**
	 * 手续费
	 */
	private BigDecimal brokerage;
	/**
	 * 代理费/后返
	 */
	private String agencyFeeRebate;
	/**
	 * 销售票面价
	 */
	private BigDecimal saleFare;
	/**
	 * 销售税费
	 */
	private BigDecimal saleTax;
	/**
	 * 销售代理费率
	 */
	private BigDecimal saleAgencyFee;
	/**
	 * 销售后反
	 */
	private BigDecimal saleRebate;
	/**
	 * 销售附加费
	 */
	private BigDecimal saleBrokerage;
	/**
	 * 销售计奖价
	 */
	private BigDecimal saleAwardPrice;
	
	
	public String getLegValue() {
		return legValue;
	}
	public void setLegValue(String legValue) {
		this.legValue = legValue;
	}
	public BigDecimal getSaleFare() {
		return saleFare;
	}
	public void setSaleFare(BigDecimal saleFare) {
		this.saleFare = saleFare;
	}
	public BigDecimal getSaleTax() {
		return saleTax;
	}
	public void setSaleTax(BigDecimal saleTax) {
		this.saleTax = saleTax;
	}
	public BigDecimal getSaleAgencyFee() {
		return saleAgencyFee;
	}
	public void setSaleAgencyFee(BigDecimal saleAgencyFee) {
		this.saleAgencyFee = saleAgencyFee;
	}
	public BigDecimal getSaleRebate() {
		return saleRebate;
	}
	public void setSaleRebate(BigDecimal saleRebate) {
		this.saleRebate = saleRebate;
	}
	public BigDecimal getSaleBrokerage() {
		return saleBrokerage;
	}
	public void setSaleBrokerage(BigDecimal saleBrokerage) {
		this.saleBrokerage = saleBrokerage;
	}
	public BigDecimal getSaleAwardPrice() {
		return saleAwardPrice;
	}
	public void setSaleAwardPrice(BigDecimal saleAwardPrice) {
		this.saleAwardPrice = saleAwardPrice;
	}
	
	public String getName() {
	
		return name;
	}
	
	public void setName(String name) {
	
		this.name = name;
	}
	
	public String getPassengerType() {
	
		return passengerType;
	}
	
	public void setPassengerType(String passengerType) {
	
		this.passengerType = passengerType;
	}
	
	public String getCertType() {
	
		return certType;
	}
	public void setCertType(String certType) {
	
		this.certType = certType;
	}
	public String getCertNo() {
	
		return certNo;
	}
	public void setCertNo(String certNo) {
	
		this.certNo = certNo;
	}
	public String getTicketNo() {
	
		return ticketNo;
	}
	public void setTicketNo(String ticketNo) {
	
		this.ticketNo = ticketNo;
	}
	public String getParentSection() {
	
		return parentSection;
	}
	public void setParentSection(String parentSection) {
	
		this.parentSection = parentSection;
	}
	public String getChildSection() {
	
		return childSection;
	}
	public void setChildSection(String childSection) {
	
		this.childSection = childSection;
	}
	
	
	public String getFareTax() {
	
		return fareTax;
	}
	
	public void setFareTax(String fareTax) {
	
		this.fareTax = fareTax;
	}
	
	public BigDecimal getBrokerage() {
	
		return brokerage;
	}
	
	public void setBrokerage(BigDecimal brokerage) {
	
		this.brokerage = brokerage;
	}
	
	public String getAgencyFeeRebate() {
	
		return agencyFeeRebate;
	}
	
	public void setAgencyFeeRebate(String agencyFeeRebate) {
	
		this.agencyFeeRebate = agencyFeeRebate;
	}
	
}
