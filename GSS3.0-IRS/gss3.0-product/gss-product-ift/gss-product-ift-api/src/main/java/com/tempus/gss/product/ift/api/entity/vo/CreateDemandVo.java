package com.tempus.gss.product.ift.api.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.product.ift.api.entity.DemandDetail;
import com.tempus.gss.product.ift.api.entity.DemandPassenger;
import com.tempus.gss.serializer.LongSerializer;

import java.io.Serializable;
import java.util.List;

public class CreateDemandVo implements Serializable {


	/**
	 * 客商编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long customerNo;


	/**
	 * 乘客类型：1.普通，2.学生，3.新移民 4.劳务 根据数据字段定义
	 */
	private String passengerType;

	/**
	 * 航程类型，1:单程; 2:往返; 3:多程
	 */
	private Integer legType;

	/**
	 * 成人数量
	 */
	private Integer adultCount;

	/**
	 * 儿童数量
	 */
	private Integer childCount;

	/**
	 * 婴儿数量
	 */
	private Integer infantCount;

	/**
	 * 联系人姓名
	 */
	private String contactName;

	/**
	 * 联系电话
	 */
	private String contactPhone;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 交易单编号.
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long transationOrderNo;

	/**
	 * 控润渠道类型 目前可用范围：301（分销商），302（集团客户）,303（散客会员）,306（体内销售）
	 */
	private String customerTypeNo;

	/**
	 * 联系人邮件
	 */
	private String contactMail;

	/**
	 * 国内客票的舱位等级主要分为头等舱（舱位代码为F）、公务舱（舱位代码为C）、经济舱（舱位代码为Y）；
	 */
	private String serviceClass;
	
	private List<DemandDetail> demandDetailList;

	/*
	* 需求单乘客集合
	* */
	private List<DemandPassenger> demandPassengerList;
	/**
	 * 首选航司
	 */
	private String firstAirLine;

	private static final long serialVersionUID = 1L;


	public Long getCustomerNo() {

		return customerNo;
	}

	public void setCustomerNo(Long customerNo) {

		this.customerNo = customerNo;
	}

	public Integer getLegType() {

		return legType;
	}

	public void setLegType(Integer legType) {

		this.legType = legType;
	}

	public Integer getAdultCount() {

		return adultCount;
	}

	public void setAdultCount(Integer adultCount) {

		this.adultCount = adultCount;
	}

	public Integer getChildCount() {

		return childCount;
	}

	public void setChildCount(Integer childCount) {

		this.childCount = childCount;
	}

	public Integer getInfantCount() {

		return infantCount;
	}

	public void setInfantCount(Integer infantCount) {

		this.infantCount = infantCount;
	}

	public String getContactName() {

		return contactName;
	}

	public void setContactName(String contactName) {

		this.contactName = contactName;
	}

	public String getContactPhone() {

		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {

		this.contactPhone = contactPhone;
	}

	public String getRemark() {

		return remark;
	}

	public void setRemark(String remark) {

		this.remark = remark;
	}


	public Long getTransationOrderNo() {

		return transationOrderNo;
	}

	public void setTransationOrderNo(Long transationOrderNo) {

		this.transationOrderNo = transationOrderNo;
	}


	public String getCustomerTypeNo() {

		return customerTypeNo;
	}

	public void setCustomerTypeNo(String customerTypeNo) {

		this.customerTypeNo = customerTypeNo;
	}

	public String getContactMail() {

		return contactMail;
	}

	public void setContactMail(String contactMail) {

		this.contactMail = contactMail;
	}

	public List<DemandDetail> getDemandDetailList() {

		return demandDetailList;
	}

	public void setDemandDetailList(List<DemandDetail> demandDetailList) {

		this.demandDetailList = demandDetailList;
	}

	public List<DemandPassenger> getDemandPassengerList() {

		return demandPassengerList;
	}

	public void setDemandPassengerList(List<DemandPassenger> demandPassengerList) {

		this.demandPassengerList = demandPassengerList;
	}

	public String getPassengerType() {

		return passengerType;
	}

	public void setPassengerType(String passengerType) {

		this.passengerType = passengerType;
	}

	public String getFirstAirLine() {

		return firstAirLine;
	}

	public void setFirstAirLine(String firstAirLine) {

		this.firstAirLine = firstAirLine;
	}

	public String getServiceClass() {
		return serviceClass;
	}

	public void setServiceClass(String serviceClass) {
		this.serviceClass = serviceClass;
	}







}
