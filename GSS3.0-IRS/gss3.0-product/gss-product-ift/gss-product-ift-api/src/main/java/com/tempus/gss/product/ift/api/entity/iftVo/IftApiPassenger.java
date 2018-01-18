package com.tempus.gss.product.ift.api.entity.iftVo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class IftApiPassenger implements Serializable {
	/**
	 * 乘客编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long passengerNo;

	/**
	 * 销售单编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long saleOrderNo;

	/**
	 * 乘客类型 1：ADT:2：CHD，3：INF
	 */
	private String passengerType;

	/**
	 * 姓
	 */
	private String surname;

	/**
	 * 名
	 */
	private String name;

	/**
	 * 证件类型 PP:护照 NI：身份证 ID:其他证件
	 */
	private String certType;

	/**
	 * 证件编号
	 */
	private String certNo;

	/**
	 * 证件有效期
	 */
	private Date certValid;

	/**
	 * 乘客出生日期
	 */
	private Date passengerBirth;

	/**
	 * 国籍
	 */
	private String nationality;

	/**
	 * 性别
	 */
	private String gender;

	/**
	 * 出票配置
	 */
	private String ticketConfig;

	/**
	 * 出票航司
	 */
	private String ticketAirline;

	/**
	 * 出票类型
	 */
	private String ticketType;

	/**
	 * 销售票价
	 */
	private BigDecimal saleFare;

	/**
	 * 销售税费
	 */
	private BigDecimal saleTax;

	/**
	 * 销售手续费
	 */
	private BigDecimal saleBrokerage;

	/**
	 * 销售代理费
	 */
	private BigDecimal saleAgencyFee;

	/**
	 * 销售后返
	 */
	private BigDecimal saleRebate;

	/**
	 * 销售计奖价
	 */
	private BigDecimal saleAwardPrice;
	
	/**
	 * 服务费
	 */
	private BigDecimal serviceCharge;

	/**
	 * 销售结算价
	 */
	private BigDecimal salePrice;

	private static final long serialVersionUID = 1L;

	public Long getPassengerNo() {
		return passengerNo;
	}

	public void setPassengerNo(Long passengerNo) {
		this.passengerNo = passengerNo;
	}


	public Long getSaleOrderNo() {
		return saleOrderNo;
	}

	public void setSaleOrderNo(Long saleOrderNo) {
		this.saleOrderNo = saleOrderNo;
	}

	public String getPassengerType() {
		return passengerType;
	}

	public void setPassengerType(String passengerType) {
		this.passengerType = passengerType;
	}
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Date getCertValid() {
		return certValid;
	}

	public void setCertValid(Date certValid) {
		this.certValid = certValid;
	}

	public Date getPassengerBirth() {
		return passengerBirth;
	}

	public void setPassengerBirth(Date passengerBirth) {
		this.passengerBirth = passengerBirth;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getTicketConfig() {
		return ticketConfig;
	}

	public void setTicketConfig(String ticketConfig) {
		this.ticketConfig = ticketConfig;
	}

	public String getTicketAirline() {
		return ticketAirline;
	}

	public void setTicketAirline(String ticketAirline) {
		this.ticketAirline = ticketAirline;
	}

	public String getTicketType() {
		return ticketType;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
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

	public BigDecimal getSaleBrokerage() {
		return saleBrokerage;
	}

	public void setSaleBrokerage(BigDecimal saleBrokerage) {
		this.saleBrokerage = saleBrokerage;
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

	public BigDecimal getSaleAwardPrice() {
		return saleAwardPrice;
	}

	public void setSaleAwardPrice(BigDecimal saleAwardPrice) {
		this.saleAwardPrice = saleAwardPrice;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public BigDecimal getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(BigDecimal serviceCharge) {
		this.serviceCharge = serviceCharge;
	}
	
}