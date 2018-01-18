package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;
import java.util.List;


public class RequirementVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 是否线上生成PNR
	 */
	private Boolean autoPNR;
	
	/**
	 * 订单类型   1.散客  2.团队
	 */
	private String orderType;
	
	/**
	 * 航程类型 航程类型，1:单程; 2:往返; 3:多程
	 */
	private Integer legType;
	
	/**
	 * 航班信息编号
	 */
	private String demandDetailNo;
	/**
	 * 航班号
	 */
	private String flight;
	/**
	 * 出发城市
	 */
	private String depAirport;
	/**
	 * 到达城市
	 */
	private String arrAirport;
	/**
	 * 舱位
	 */
	private String space;
	/**
	 * 起飞时间
	 */
	private String depTime;
	/**
	 * 到达时间
	 */
	private String arrTime;
	/**
	 * 乘客信息编号
	 */
	private String demandPassengerNo;
	/**
	 * 乘客姓名
	 */
	private String surname;
	/**
	 * 乘客类型
	 */
	private String passengerType;
	/**
	 * 证件类型
	 */
	private String certType;
	/**
	 * 证件编号
	 */
	private String certNo;
	/**
	 * 性别
	 */
	private String gender;

	/**
	 * 销售
	 */
	private List<SaleOrderDetailVo> saleOrderDetailVoList;
	
	/**
	 * 采购
	 */
	private List<BuyOrderDetailVo> buyOrderDetailVoList; 
	/**
	 * 出票航司
	 */
	private String office;
	/**
	 * 打票配置
	 */
	private String ticketConfig;
	/**
	 * 出票类型
	 */
	private String ticketType;
	/**
	 * 备注
	 */
	private String remarks;
	public Boolean getAutoPNR() {
		return autoPNR;
	}
	public void setAutoPNR(Boolean autoPNR) {
		this.autoPNR = autoPNR;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public Integer getLegType() {
		return legType;
	}
	public void setLegType(Integer legType) {
		this.legType = legType;
	}
	public String getDemandDetailNo() {
		return demandDetailNo;
	}
	public void setDemandDetailNo(String demandDetailNo) {
		this.demandDetailNo = demandDetailNo;
	}
	public String getFlight() {
		return flight;
	}
	public void setFlight(String flight) {
		this.flight = flight;
	}
	public String getDepAirport() {
		return depAirport;
	}
	public void setDepAirport(String depAirport) {
		this.depAirport = depAirport;
	}
	public String getArrAirport() {
		return arrAirport;
	}
	public void setArrAirport(String arrAirport) {
		this.arrAirport = arrAirport;
	}
	public String getSpace() {
		return space;
	}
	public void setSpace(String space) {
		this.space = space;
	}
	
	public String getDepTime() {
		return depTime;
	}
	public void setDepTime(String depTime) {
		this.depTime = depTime;
	}
	public String getArrTime() {
		return arrTime;
	}
	public void setArrTime(String arrTime) {
		this.arrTime = arrTime;
	}
	public String getDemandPassengerNo() {
		return demandPassengerNo;
	}
	public void setDemandPassengerNo(String demandPassengerNo) {
		this.demandPassengerNo = demandPassengerNo;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getOffice() {
		return office;
	}
	public void setOffice(String office) {
		this.office = office;
	}
	public String getTicketConfig() {
		return ticketConfig;
	}
	public void setTicketConfig(String ticketConfig) {
		this.ticketConfig = ticketConfig;
	}
	public String getTicketType() {
		return ticketType;
	}
	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public List<SaleOrderDetailVo> getSaleOrderDetailVoList() {
		return saleOrderDetailVoList;
	}
	public void setSaleOrderDetailVoList(List<SaleOrderDetailVo> saleOrderDetailVoList) {
		this.saleOrderDetailVoList = saleOrderDetailVoList;
	}
	public List<BuyOrderDetailVo> getBuyOrderDetailVoList() {
		return buyOrderDetailVoList;
	}
	public void setBuyOrderDetailVoList(List<BuyOrderDetailVo> buyOrderDetailVoList) {
		this.buyOrderDetailVoList = buyOrderDetailVoList;
	}
	
	
}
