/**
 * SaleOrderVo.java
 * com.tempus.gss.product.ins.api.entity.vo
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2016年10月19日 		shuo.cheng
 *
 * Copyright (c) 2016, TNT All Rights Reserved.
*/

package com.tempus.gss.product.ins.api.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.cps.entity.Customer;
import com.tempus.gss.order.entity.BuyOrder;
import com.tempus.gss.order.entity.SaleOrder;
import com.tempus.gss.product.ins.api.entity.Insurance;
import com.tempus.gss.product.ins.api.entity.SaleOrderDetail;
import com.tempus.gss.serializer.LongSerializer;

/**
 * ClassName:SaleOrderVo
 *
 * @author   shuo.cheng
 * @version  
 * @since    Ver 1.1
 * @Date	 2016年10月19日		上午10:40:37
 *
 * @see 	 
 *  
 */
@Alias("insSaleOrderExtVo")
public class SaleOrderExtVo implements Serializable {
	/**
	 * 订单编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long saleOrderNo;
	private Long id;

	/**
	 * 数据归属单位
	 */
	private Integer owner;

	/**
	 * 保险销售单
	 */
	private SaleOrder saleOrder;

	/**
	 * 采购单编号
	 */
	private Long buyOrderNo;

	/**
	 * 保险采购单
	 */
	private BuyOrder buyOrder;

	/**
	 * 保险产品编号
	 */
	private Long insuranceNo;

	/**
	 * 保险产品
	 */
	private Insurance insurance;

	/**
	 * 投保单号
	 */
	private String proposalNo;

	/**
	 * 订单号
	 */
	private String orderNo;

	/**
	 * 订单类型 1：线上 2：线下
	 */
	private Integer orderType;

	/**
	 * 保单下载地址
	 */
	private String policyUrl;

	/**
	 * 保单号
	 */
	private String policyNo;

	/**
	 * 交易流水号
	 */
	private String transactionId;

	/**
	 * 产品code
	 */
	private String productKey;

	/**
	 * 目的地
	 */
	private String destination;

	/**
	 * 保单生效日期 yyyy-MM-dd HH:mm:ss
	 */
	private Date effectDate;

	/**
	 * 保单失效日期 yyyy-MM-dd HH:mm:ss
	 */
	private Date expireDate;

	/**
	 * 投保日期 yyyy-MM-dd HH:mm:ss
	 */
	private Date issueDate;

	/**
	 * 投保开始日期 yyyy-MM-dd
	 */
	private Date issueStartDate;

	/**
	 * 投保结束日期 yyyy-MM-dd
	 */
	private Date issueEndDate;

	/**
	 * 投保人类型 1-个人 2-企业
	 */
	private Integer holderType;

	/**
	 * 投保人姓名
	 */
	private String holderName;

	/**
	 * 投保人证件类型
	 */
	private Integer holderCertiType;

	/**
	 * 投保人证件号码
	 */
	private String holderCertiNo;

	/**
	 * 投保人性别 1-男 2-女
	 */
	private Integer holderSex;

	/**
	 * yyyy-MM-dd 投保人出生日
	 */
	private Date holderBirthday;

	/**
	 * 投保人电子邮箱地址
	 */
	private String holderEmail;

	/**
	 * 投保人手机号码
	 */
	private String holderPhone;

	/**
	 * 销售价
	 */
	private BigDecimal salePrice;

	/**
	 * 采购价
	 */
	private BigDecimal buyPrice;


	/**
	 * 总保费
	 */
	private BigDecimal totalPremium;

	/**
	 * 旅行天数
	 */
	private Integer travelDay;

	/**
	 * 被保险人个数
	 */
	private Integer insuredCount;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 是否团单 1-是 2-否
	 */
	private Short isTeam;

	/**
	 * 渠道名称
	 */
	private String sourceName;

	/**
	 * 拓展字段json串
	 */
	private String extendedFieldsJson;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	/**
	 * 创建者
	 */
	private String creator;

	/**
	 * 操作人 默认为：sys
	 */
	private String modifier;

	/**
	 * 删除标志 0 无效 已删除 1 有效
	 */
	private Boolean valid;
	/**
	 * 子保单列表
	 */
	private List<SaleOrderDetailVo> subPolicyList;
	/**
	 * 起保日期
	 */
	private String startDate;
	/**
	 * 终保日期
	 */
	private String endDate;
	/**
	 * 客户搜索时选择的日期类型
	 */
    private int dateType;
	/**
	 * 险种
	 */
	private String insureType;

	/**
	 * 保险名称
	 */
	private String insuranceName;

	/**
	 * 被保人姓名
	 */
	private String insuredName;

	/**
	 * 支付状态
	 */
	private String payStatus;

	/**
	 * 交易单号
	 */
	private String transactionOrderNo;

	/**
	 * 订单子状态
	 */
	private String orderChildStatus;

	/**
	 * 保险子订单集合
	 */
	private List<SaleOrderDetail> saleOrderDetailList;

	/**
	 * PNR,关联航班信息
	 */
	private String pnr;

	/** 客户类型 */
	@JsonSerialize(using = LongSerializer.class)
	private Long customerTypeNo;

	/** 客户编号 */
	@JsonSerialize(using = LongSerializer.class)
	private Long customerNo;
	/** 是否查询下级客商订单 */
	private boolean isLowerLevel;
	/** 下级客商信息 */
	private List<Customer> lowerCustomers;
	/**
	 * 投保生效开始时间
	 */
	private String startEffectTime;
	/**
	 * 投保生效结束时间
	 */
	private String endEffectTime;
	/**
	 * 投保开始时间
	 */
	private String startInsureTime;
	/**
	 * 投保结束时间
	 */
	private String endInsureTime;
	/**
	 * 客户名称
	 */
	private String customerName;
	/**
	 * 客户类型
	 */
	private String customerType;
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public int getDateType() {
		return dateType;
	}

	public void setDateType(int dateType) {
		this.dateType = dateType;
	}
	public String getStartEffectTime() {
		return startEffectTime;
	}

	public void setStartEffectTime(String startEffectTime) {
		this.startEffectTime = startEffectTime;
	}

	public String getEndEffectTime() {
		return endEffectTime;
	}

	public void setEndEffectTime(String endEffectTime) {
		this.endEffectTime = endEffectTime;
	}
	public boolean isLowerLevel() {
		return isLowerLevel;
	}
	public void setLowerLevel(boolean isLowerLevel) {
		this.isLowerLevel = isLowerLevel;
	}

	public String getStartInsureTime() {
		return startInsureTime;
	}

	public void setStartInsureTime(String startInsureTime) {
		this.startInsureTime = startInsureTime;
	}

	public String getEndInsureTime() {
		return endInsureTime;
	}

	public void setEndInsureTime(String endInsureTime) {
		this.endInsureTime = endInsureTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private static final long serialVersionUID = 1L;
	
	public Long getCustomerTypeNo() {

		return customerTypeNo;
	}

	public List<Customer> getLowerCustomers() {
		return lowerCustomers;
	}

	public void setLowerCustomers(List<Customer> lowerCustomers) {
		this.lowerCustomers = lowerCustomers;
	}

	public void setCustomerTypeNo(Long customerTypeNo) {

		this.customerTypeNo = customerTypeNo;
	}

	public Long getCustomerNo() {

		return customerNo;
	}

	public void setCustomerNo(Long customerNo) {

		this.customerNo = customerNo;
	}

	public String getPnr() {
	
		return pnr;
	}
	
	public void setPnr(String pnr) {
	
		this.pnr = pnr;
	}

	public String getOrderChildStatus() {

		return orderChildStatus;
	}

	public void setOrderChildStatus(String orderChildStatus) {

		this.orderChildStatus = orderChildStatus;
	}

	public String getTransactionOrderNo() {

		return transactionOrderNo;
	}

	public void setTransactionOrderNo(String transactionOrderNo) {

		this.transactionOrderNo = transactionOrderNo;
	}

	public String getPayStatus() {

		return payStatus;
	}

	public void setPayStatus(String payStatus) {

		this.payStatus = payStatus;
	}

	public String getInsuredName() {

		return insuredName;
	}

	public void setInsuredName(String insuredName) {

		this.insuredName = insuredName;
	}

	public String getInsureType() {

		return insureType;
	}

	public void setInsureType(String insureType) {

		this.insureType = insureType;
	}

	public Long getSaleOrderNo() {
		return saleOrderNo;
	}

	public void setSaleOrderNo(Long saleOrderNo) {
		this.saleOrderNo = saleOrderNo;
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

	public Long getBuyOrderNo() {
		return buyOrderNo;
	}

	public void setBuyOrderNo(Long buyOrderNo) {
		this.buyOrderNo = buyOrderNo;
	}

	public Long getInsuranceNo() {
		return insuranceNo;
	}

	public void setInsuranceNo(Long insuranceNo) {
		this.insuranceNo = insuranceNo;
	}

	public String getProposalNo() {
		return proposalNo;
	}

	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public String getPolicyUrl() {
		return policyUrl;
	}

	public void setPolicyUrl(String policyUrl) {
		this.policyUrl = policyUrl;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getProductKey() {
		return productKey;
	}

	public void setProductKey(String productKey) {
		this.productKey = productKey;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Date getEffectDate() {
		return effectDate;
	}

	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Integer getHolderType() {
		return holderType;
	}

	public void setHolderType(Integer holderType) {
		this.holderType = holderType;
	}

	public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	public Integer getHolderCertiType() {
		return holderCertiType;
	}

	public void setHolderCertiType(Integer holderCertiType) {
		this.holderCertiType = holderCertiType;
	}

	public String getHolderCertiNo() {
		return holderCertiNo;
	}

	public void setHolderCertiNo(String holderCertiNo) {
		this.holderCertiNo = holderCertiNo;
	}

	public Integer getHolderSex() {
		return holderSex;
	}

	public void setHolderSex(Integer holderSex) {
		this.holderSex = holderSex;
	}

	public Date getHolderBirthday() {
		return holderBirthday;
	}

	public void setHolderBirthday(Date holderBirthday) {
		this.holderBirthday = holderBirthday;
	}

	public String getHolderEmail() {
		return holderEmail;
	}

	public void setHolderEmail(String holderEmail) {
		this.holderEmail = holderEmail;
	}

	public String getHolderPhone() {
		return holderPhone;
	}

	public void setHolderPhone(String holderPhone) {
		this.holderPhone = holderPhone;
	}

	public BigDecimal getTotalPremium() {
		return totalPremium;
	}

	public void setTotalPremium(BigDecimal totalPremium) {
		this.totalPremium = totalPremium;
	}

	public Integer getTravelDay() {
		return travelDay;
	}

	public void setTravelDay(Integer travelDay) {
		this.travelDay = travelDay;
	}

	public Integer getInsuredCount() {
		return insuredCount;
	}

	public void setInsuredCount(Integer insuredCount) {
		this.insuredCount = insuredCount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Short getIsTeam() {

		return isTeam;
	}

	public void setIsTeam(Short isTeam) {

		this.isTeam = isTeam;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getExtendedFieldsJson() {
		return extendedFieldsJson;
	}

	public void setExtendedFieldsJson(String extendedFieldsJson) {
		this.extendedFieldsJson = extendedFieldsJson;
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

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Boolean getValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	public List<SaleOrderDetail> getSaleOrderDetailList() {

		return saleOrderDetailList;
	}

	public void setSaleOrderDetailList(List<SaleOrderDetail> saleOrderDetailList) {

		this.saleOrderDetailList = saleOrderDetailList;
	}

	public Insurance getInsurance() {

		return insurance;
	}

	public void setInsurance(Insurance insurance) {

		this.insurance = insurance;
	}

	public SaleOrder getSaleOrder() {

		return saleOrder;
	}

	public void setSaleOrder(SaleOrder saleOrder) {

		this.saleOrder = saleOrder;
	}

	public BuyOrder getBuyOrder() {

		return buyOrder;
	}

	public void setBuyOrder(BuyOrder buyOrder) {

		this.buyOrder = buyOrder;
	}

	public BigDecimal getSalePrice() {

		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {

		this.salePrice = salePrice;
	}

	public BigDecimal getBuyPrice() {

		return buyPrice;
	}

	public void setBuyPrice(BigDecimal buyPrice) {

		this.buyPrice = buyPrice;
	}
	
	public List<SaleOrderDetailVo> getSubPolicyList() {
	
		return subPolicyList;
	}

	public void setSubPolicyList(List<SaleOrderDetailVo> subPolicyList) {
	
		this.subPolicyList = subPolicyList;
	}

	public String getStartDate() {
	
		return startDate;
	}
	
	public void setStartDate(String startDate) {
	
		this.startDate = startDate;
	}

	public String getEndDate() {
	
		return endDate;
	}
	
	public void setEndDate(String endDate) {
	
		this.endDate = endDate;
	}

	public Date getIssueStartDate() {

		return issueStartDate;
	}

	public void setIssueStartDate(Date issueStartDate) {

		this.issueStartDate = issueStartDate;
	}

	public Date getIssueEndDate() {

		return issueEndDate;
	}

	public void setIssueEndDate(Date issueEndDate) {

		this.issueEndDate = issueEndDate;
	}

	public String getInsuranceName() {
		return insuranceName;
	}

	public void setInsuranceName(String insuranceName) {
		this.insuranceName = insuranceName;
	}
}

