package com.tempus.gss.product.ift.api.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Demand implements Serializable {

	/**
	 * 需求单编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long demandNo;

	/**
	 * Id
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long id;

	/**
	 * 数据归属单位
	 */
	private Integer owner;

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
	 * 启用状态 1：待核价，2：已核价，3：已取消
	 */
	private String status;

	/**
	 * 核价人ID
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long auditor;

	/**
	 * 核价时间
	 */
	private Date auditTime;

	/**
	 * 交易单编号.
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long transationOrderNo;

	/**
	 * 删除标志 0 无效 已删除 1 有效
	 */
	private Byte valid;

	/**
	 * 操作人 默认为：sys
	 */
	private String modifier;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	/**
	 * 控润渠道类型 目前可用范围：301（分销商），302（集团客户）,303（散客会员）,306（体内销售）
	 */
	private String customerTypeNo;

	/**
	 * 联系人邮件
	 */
	private String contactMail;

	/**
	 * 创建人
	 */
	private String creator;
	
	/**
	 * 国内客票的舱位等级主要分为头等舱（舱位代码为F）、公务舱（舱位代码为C）、经济舱（舱位代码为Y）；
	 */
	private String serviceClass;
	
	/**
	 * 将销售单锁定的用户的Id 有大于0的值，表示已被用户锁定，是该用户的Id.
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long locker;
	
	/**
	 * 订单类型
	 */
	/*private String orderType;*/

	private List<DemandDetail> demandDetailList;

	/***
	* 需求单乘客集合
	* */
	private List<DemandPassenger> demandPassengerList;
	/**
	 * 首选航司
	 */
	private String firstAirLine;
	
	/**
	 * 是否团队票标志 0 散客 1 团队
	 */
	private Byte isTeam;
	
	/**
	 * 团队编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long teamNo;

	public Long getTeamNo() {
		return teamNo;
	}

	public void setTeamNo(Long teamNo) {
		this.teamNo = teamNo;
	}

	private static final long serialVersionUID = 1L;

	public Long getDemandNo() {

		return demandNo;
	}

	public void setDemandNo(Long demandNo) {

		this.demandNo = demandNo;
	}

	public Long getId() {

		return id;
	}

	public void setId(Long id) {

		this.id = id;
	}

	public Integer getOwner() {

		return owner;
	}

	public void setOwner(Integer owner) {

		this.owner = owner;
	}

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

	public String getStatus() {

		return status;
	}

	public void setStatus(String status) {

		this.status = status;
	}

	public Long getAuditor() {

		return auditor;
	}

	public void setAuditor(Long auditor) {

		this.auditor = auditor;
	}

	public Date getAuditTime() {

		return auditTime;
	}

	public void setAuditTime(Date auditTime) {

		this.auditTime = auditTime;
	}

	public Long getTransationOrderNo() {

		return transationOrderNo;
	}

	public void setTransationOrderNo(Long transationOrderNo) {

		this.transationOrderNo = transationOrderNo;
	}

	public Byte getValid() {

		return valid;
	}

	public void setValid(Byte valid) {

		this.valid = valid;
	}

	public String getModifier() {

		return modifier;
	}

	public void setModifier(String modifier) {

		this.modifier = modifier;
	}

	public Date getCreateTime() {

		return createTime;
	}

	public void setCreateTime(Date createTime) {

		this.createTime = createTime;
	}

	public Date getModifyTime() {

		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {

		this.modifyTime = modifyTime;
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

	public String getCreator() {

		return creator;
	}

	public void setCreator(String creator) {

		this.creator = creator;
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

	/*public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}*/

	public Long getLocker() {
		return locker;
	}

	public void setLocker(Long locker) {
		this.locker = locker;
	}

	public Byte getIsTeam() {
		return isTeam;
	}

	public void setIsTeam(Byte isTeam) {
		this.isTeam = isTeam;
	}






}
