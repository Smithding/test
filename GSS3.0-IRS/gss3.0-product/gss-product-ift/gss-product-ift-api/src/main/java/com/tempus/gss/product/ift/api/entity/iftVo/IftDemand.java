package com.tempus.gss.product.ift.api.entity.iftVo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;

import java.io.Serializable;
import java.util.List;

public class IftDemand implements Serializable {

	/**
	 * 需求单编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long demandNo;

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
	 * 联系人邮件
	 */
	private String contactMail;

	
	/**
	 * 国内客票的舱位等级主要分为头等舱（舱位代码为F）、公务舱（舱位代码为C）、经济舱（舱位代码为Y）；
	 */
	private String serviceClass;

	private List<IftDemandDetail> demandDetailList;

	/** 
	* 需求单乘客集合
	*/
	private List<IftDemandPassenger> demandPassengerList;

	private static final long serialVersionUID = 1L;

	public Long getDemandNo() {

		return demandNo;
	}

	public void setDemandNo(Long demandNo) {

		this.demandNo = demandNo;
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

	public String getContactMail() {

		return contactMail;
	}

	public void setContactMail(String contactMail) {

		this.contactMail = contactMail;
	}

	public List<IftDemandDetail> getDemandDetailList() {

		return demandDetailList;
	}

	public void setDemandDetailList(List<IftDemandDetail> demandDetailList) {

		this.demandDetailList = demandDetailList;
	}

	public List<IftDemandPassenger> getDemandPassengerList() {

		return demandPassengerList;
	}

	public void setDemandPassengerList(List<IftDemandPassenger> demandPassengerList) {

		this.demandPassengerList = demandPassengerList;
	}

	public String getPassengerType() {

		return passengerType;
	}

	public void setPassengerType(String passengerType) {

		this.passengerType = passengerType;
	}

	public String getServiceClass() {
		return serviceClass;
	}

	public void setServiceClass(String serviceClass) {
		this.serviceClass = serviceClass;
	}

}
