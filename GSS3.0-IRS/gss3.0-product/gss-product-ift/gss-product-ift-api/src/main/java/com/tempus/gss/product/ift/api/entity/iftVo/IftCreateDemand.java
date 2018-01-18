package com.tempus.gss.product.ift.api.entity.iftVo;


import java.io.Serializable;
import java.util.List;

public class IftCreateDemand implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 成人数量
	 */
	private Integer adultCount;
	
	/**
	 * 乘客类型：1.普通，2.学生，3.新移民 4.劳务 根据数据字段定义
	 */
	private String passengerType;
	
	/**
	 * 国内客票的舱位等级主要分为头等舱（舱位代码为F）、公务舱（舱位代码为C）、经济舱（舱位代码为Y）；
	 */
	private String serviceClass;
	
	/**
	 * 联系人姓名
	 */
	private String contactName;

	/**
	 * 联系电话
	 */
	private String contactPhone;
	
	/**
	 * 航程类型，1:单程; 2:往返; 3:多程
	 */
	private Integer legType;
	
	/**
	 * 首选航司
	 */
	private String firstAirLine;
	
	/**
	 * 备注
	 */
	private String remark;
	
	private List<IftDemandDetail> demandDetailList;
	
	private List<IftDemandPassenger> demandPassengerList;

	public Integer getAdultCount() {
		return adultCount;
	}

	public void setAdultCount(Integer adultCount) {
		this.adultCount = adultCount;
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

	public Integer getLegType() {
		return legType;
	}

	public void setLegType(Integer legType) {
		this.legType = legType;
	}

	public String getFirstAirLine() {
		return firstAirLine;
	}

	public void setFirstAirLine(String firstAirLine) {
		this.firstAirLine = firstAirLine;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}