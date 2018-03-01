package com.tempus.gss.product.ins.api.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class InsuranceOrderExport implements Serializable{
/*订单编号*/
private String saleOrderNo;
/*交易流水号*/
private String transationId;
/*保险名称*/
private String insuranceName;
/*保单生效日期*/
private String effectDate;
/*保单失效日期*/
private String expireDate;

/*保单状态*/
private String status;
/*支付时间*/
private String payTime;
/*支付状态*/
private String payStatus;
/*支付方式*/
private String payWay;
/*结算类型*/
private String Type;
/*公司名称*/
private String companyName;
/*订票员*/
private String Ticker;
/*被保人*/
private String Insure;
/*证件类型*/
private String cardType;
/*证件号码*/
private String cardNo;
/*保险份数*/
private String insuranceCount;
/*采购价*/
private BigDecimal buyPrice;
/*保费*/
private BigDecimal facePrice;
/*实收*/
private BigDecimal realPrice;
/*创建时间*/
private String issueDate;
/*投保时间*/
private String fundDate;
/*利润*/
private BigDecimal Profit;
/*航班号*/
private String flightNo;
/*起飞时间*/
private String flightTime;

public String getPolicyNo() {
	return policyNo;
}
/*客户类型*/
private String customerType;
/*客户名称*/
private String customerName;
/*国内还是国际*/
private String internatOrcivil;
/*保单号*/
private String policyNo;
public String getPnr() {
	return pnr;
}
public void setPnr(String pnr) {
	this.pnr = pnr;
}
private String pnr;
public String getSaleOrderNo() {
	return saleOrderNo;
}
public void setSaleOrderNo(String saleOrderNo) {
	this.saleOrderNo = saleOrderNo;
}
public String getFundDate() {
	return fundDate;
}
public void setFundDate(String fundDate) {
	this.fundDate = fundDate;
}
public String getTransationId() {
	return transationId;
}
public void setTransationId(String transationId) {
	this.transationId = transationId;
}
public String getInsuranceName() {
	return insuranceName;
}
public void setInsuranceName(String insuranceName) {
	this.insuranceName = insuranceName;
}
public String getEffectDate() {
	return effectDate;
}
public void setEffectDate(String effectDate) {
	this.effectDate = effectDate;
}
public String getExpireDate() {
	return expireDate;
}
public void setExpireDate(String expireDate) {
	this.expireDate = expireDate;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getPayTime() {
	return payTime;
}
public void setPayTime(String payTime) {
	this.payTime = payTime;
}
public String getPayStatus() {
	return payStatus;
}
public void setPayStatus(String payStatus) {
	this.payStatus = payStatus;
}
public String getPayWay() {
	return payWay;
}
public void setPayWay(String payWay) {
	this.payWay = payWay;
}
public String getType() {
	return Type;
}
public void setType(String type) {
	Type = type;
}
public String getCompanyName() {
	return companyName;
}
public void setCompanyName(String companyName) {
	this.companyName = companyName;
}
public String getTicker() {
	return Ticker;
}
public void setTicker(String ticker) {
	Ticker = ticker;
}
public String getInsure() {
	return Insure;
}
public void setInsure(String insure) {
	Insure = insure;
}
public String getCardType() {
	return cardType;
}
public void setCardType(String cardType) {
	this.cardType = cardType;
}
public String getCardNo() {
	return cardNo;
}
public void setCardNo(String cardNo) {
	this.cardNo = cardNo;
}
public String getInsuranceCount() {
	return insuranceCount;
}
public void setInsuranceCount(String insuranceCount) {
	this.insuranceCount = insuranceCount;
}
public BigDecimal getBuyPrice() {
	return buyPrice;
}
public void setBuyPrice(BigDecimal buyPrice) {
	this.buyPrice = buyPrice;
}
public BigDecimal getFacePrice() {
	return facePrice;
}
public void setFacePrice(BigDecimal facePrice) {
	this.facePrice = facePrice;
}
public BigDecimal getRealPrice() {
	return realPrice;
}
public void setRealPrice(BigDecimal realPrice) {
	this.realPrice = realPrice;
}
public String getIssueDate() {
	return issueDate;
}
public void setIssueDate(String issueDate) {
	this.issueDate = issueDate;
}
public BigDecimal getProfit() {
	return Profit;
}
public void setProfit(BigDecimal profit) {
	Profit = profit;
}
public String getFlightNo() {
	return flightNo;
}
public void setFlightNo(String flightNo) {
	this.flightNo = flightNo;
}
public String getFlightTime() {
	return flightTime;
}
public void setFlightTime(String flightTime) {
	this.flightTime = flightTime;
}
public String getCustomerType() {
	return customerType;
}
public void setCustomerType(String customerType) {
	this.customerType = customerType;
}
public String getCustomerName() {
	return customerName;
}
public void setCustomerName(String customerName) {
	this.customerName = customerName;
}
public String getInternatOrcivil() {
	return internatOrcivil;
}
public void setInternatOrcivil(String internatOrcivil) {
	this.internatOrcivil = internatOrcivil;
}
public void setPolicyNo(String policyNo) {
	this.policyNo = policyNo;
}
}
