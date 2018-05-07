package com.tempus.gss.product.ins.api.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tempus.gss.product.ins.api.entity.SaleOrderDetail;
import com.tempus.gss.product.ins.api.entity.SaleOrderDetailForJ;

/**
 * 
 * ClassName:InsureRequestVo Function: 投保请求对象
 *
 * @author shuo.cheng
 * @version
 * @since Ver 1.1
 * @Date 2016 2016年10月27日 上午8:43:34
 *
 * @see
 *
 */
@JsonInclude(Include.NON_NULL)
public class InsureRequestVo implements Serializable {
	private static final long serialVersionUID = 1L;

	/*
	*产品code
	 *  */
	private String productKey;
	/*
	* 目的地
	* */
	private String destination;
	/*
	* 保单生效日期 yyyy-MM-dd HH:mm:ss
	* */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date effectDate;
	/*
	*保单失效日期 yyyy-MM-dd HH:mm:ss
	* */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date expireDate;

	/*
	*投保日期 yyyy-MM-dd HH:mm:ss
	* */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date issueDate;
	/*
	* 投保人类型 1-个人 2-企业
	* */
	private Integer policyHolderType;
	/*
	* 投保人姓名
	* */
	private String policyHolderName;
	/*
	*个人 1:身份证,2:护照,3:出生证,4:驾照,5:军官证,6:其他
	* 企业：1:税务登记证、2:营业执照、3:组织机构证、4:组织机构代码、5：统一社会信用代码
	* */
	private Integer policyHolderCertiType;
	/*
	*  投保人证件号码
	* */
	private String policyHolderCertiNo;
	/*
	* 投保人性别 1-男 2-女
	* */
	private Integer policyHolderSex;
	/*
	* yyyy-MM-dd 投保人出生日
	* */
/*	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")*/
	private String policyHolderBirthday;
	/*
	* 投保人电子邮箱地址
	* */
	private String policyHolderEmail;
	/*
	*投保人手机号码
	* */
	private String policyHolderPhone;
	/*
	* 总保费
	* */
	private BigDecimal totalPremium;
	/*
	*  旅行天数
	* */
	private Integer travelDay;
	/*
	* 被保险人个数
	* */
	private Integer insuredCount;
	/*
	* 被保人列表(正对君龙保险)
	* */
	@JsonProperty("insuredList")
	private List<SaleOrderDetail> insuredList;
	/*
	* 被保人列表
	* */
	@JsonProperty("insuredList")
	private List<SaleOrderDetail> SaleOrderDetailList;
	/*
	 * 航班号
	 */
	private String transNum;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date dptTime;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date arrTime;
	
	private Long orderId;


	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Date getArrTime() {
		return arrTime;
	}

	public void setArrTime(Date arrTime) {
		this.arrTime = arrTime;
	}

	public Date getDptTime() {
		return dptTime;
	}

	public void setDptTime(Date dptTime) {
		this.dptTime = dptTime;
	}

	public String getTransNum() {
		return transNum;
	}

	public void setTransNum(String transNum) {
		this.transNum = transNum;
	}

	public List<SaleOrderDetail> getInsuredList() {
		return insuredList;
	}

	public void setInsuredList(List<SaleOrderDetail> insuredList) {
		this.insuredList = insuredList;
	}

	/*
	* 备注
	* */
	private String remark;
	/*
	* 是否团单 1-是 2-否
	* */
	private Short isTeam;
	/*
	* 渠道名称
	* */
	private String sourceName;
	/*
	* 签名
	* */
	private String sign;
	/*
	* 拓展字段json串
	* */
	private String extendedFieldSJson;
	public List<SaleOrderDetail> getSaleOrderDetailList() {
		return SaleOrderDetailList;
	}

	public void setSaleOrderDetailList(List<SaleOrderDetail> saleOrderDetailList) {
		SaleOrderDetailList = saleOrderDetailList;
	}

	public String getDestination() {

		return destination;
	}

	public Integer getPolicyHolderSex() {

		return policyHolderSex;
	}

	public void setPolicyHolderSex(Integer policyHolderSex) {

		this.policyHolderSex = policyHolderSex;
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

	public String getSign() {

		return sign;
	}

	public void setSign(String sign) {

		this.sign = sign;
	}

	public String getProductKey() {

		return productKey;
	}

	public void setProductKey(String productKey) {

		this.productKey = productKey;
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

	public Integer getInsuredCount() {

		return insuredCount;
	}

	public void setInsuredCount(Integer insuredCount) {

		this.insuredCount = insuredCount;
	}

	public Date getIssueDate() {

		return issueDate;
	}

	public void setIssueDate(Date issueDate) {

		this.issueDate = issueDate;
	}


	public String getPolicyHolderCertiNo() {

		return policyHolderCertiNo;
	}
	public void setPolicyHolderCertiNo(String policyHolderCertiNo) {

		this.policyHolderCertiNo = policyHolderCertiNo;
	}

	public String getPolicyHolderBirthday() {
		return policyHolderBirthday;
	}

	public void setPolicyHolderBirthday(String policyHolderBirthday) {
		this.policyHolderBirthday = policyHolderBirthday;
	}

	public Integer getPolicyHolderCertiType() {

		return policyHolderCertiType;
	}

	public void setPolicyHolderCertiType(Integer policyHolderCertiType) {

		this.policyHolderCertiType = policyHolderCertiType;
	}

	public String getPolicyHolderEmail() {

		return policyHolderEmail;
	}

	public void setPolicyHolderEmail(String policyHolderEmail) {

		this.policyHolderEmail = policyHolderEmail;
	}

	public String getPolicyHolderName() {

		return policyHolderName;
	}

	public void setPolicyHolderName(String policyHolderName) {

		this.policyHolderName = policyHolderName;
	}

	public String getPolicyHolderPhone() {

		return policyHolderPhone;
	}

	public void setPolicyHolderPhone(String policyHolderPhone) {

		this.policyHolderPhone = policyHolderPhone;
	}

	public String getRemark() {

		return remark;
	}

	public void setRemark(String remark) {

		this.remark = remark;
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

	public Integer getPolicyHolderType() {

		return policyHolderType;
	}

	public void setPolicyHolderType(Integer policyHolderType) {

		this.policyHolderType = policyHolderType;
	}


	public String getExtendedFieldSJson() {

		return extendedFieldSJson;
	}

	public void setExtendedFieldSJson(String extendedFieldSJson) {

		this.extendedFieldSJson = extendedFieldSJson;
	}

}