package com.tempus.gss.product.ift.api.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;
import org.springframework.data.annotation.Transient;

public class Policy implements Serializable {

	/**
	 * 政策编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long policyNo;

	/**
	 * 编号
	 */
	private Long id;

	/**
	 * 数据归属单位
	 */
	private Integer owner;

	/**
	 * 出票航司
	 */
	private String airline;

	/**
	 * 投放分销商
	 */
	private String distributor;

	/**
	 * 产品商
	 */
	private String productor;

	/**
	 * 销售配置
	 */
	private String saleConfig;
	
	/**
	 * 出票类型
	 */
	private String ticketWay;

	/**
	 * 产品号
	 */
	private String productNo;

	/**
	 * 旅客类型 ADT/CNN
	 */
	private String travellerType;

	/**
	 * 旅客人数下限
	 */
	private Integer travellerLimit;

	/**
	 * 团队出票类型 0：不限，1：散客，2：团队
	 */
	private Integer teamTicketType;

	/**
	 * 行程类型 1：单程，2：往返
	 */
	private Integer tripType;

	/**
	 * 去程起点
	 */
	private String goStart;

	/**
	 * 去程起点除外
	 */
	private String exceptGoStart;

	/**
	 * 去程终点/折返点
	 */
	private String goEnd;

	/**
	 * 去程终点/折返点除外
	 */
	private String exceptGoEnd;

	/**
	 * 回程终点
	 */
	private String backEnd;

	/**
	 * 回程终点除外
	 */
	private String exceptBackEnd;

	/**
	 * 跨航线组合匹配
	 */
	private Boolean allowCrossLineMatch;

	/**
	 * 去程旅行日期
	 */
	private String travelDate;

	/**
	 * 回程旅行日期
	 */
	private String backDate;

	/**
	 * 出票日期
	 */
	private String ticketDate;

	/**
	 * 全程是否同一承运人
	 */
	private Boolean isSameCarrier;

	/**
	 * 是否代码共享
	 */
	private Boolean isCodeShare;

	/**
	 * 共享航司 CA/MU
	 */
	private String shareAirline;

	/**
	 * 是否必须直飞
	 */
	private Boolean onlyDirectFlight;

	/**
	 * Q值返佣
	 */
	private Boolean qRebate;

	/**
	 * S值返佣
	 */
	private Boolean sRebate;

	/**
	 * YR YQ返佣
	 */
	private Boolean yryqRebate;

	/**
	 * 去程不能经过
	 */
	private String goNotFlight;

	/**
	 * 去程必须经过
	 */
	private String goMustFlight;

	/**
	 * 回程不能经过
	 */
	private String backNotFlight;

	/**
	 * 回程必须经过
	 */
	private String backMustFlight;

	/**
	 * 去程仅限航班号
	 */
	private String goFlightNo;

	/**
	 * 回程仅限航班号
	 */
	private String backFlightNo;

	/**
	 * 排除航班号
	 */
	private String exceptFlightNo;

	/**
	 * 代理费
	 */
	private BigDecimal agencyFee;

	/**
	 * 政策开始日期
	 */
	private String policyEffectStart;

	/**
	 * 政策结束日期
	 */
	private String policyEffectEnd;

	/**
	 * 销售备注
	 */
	private String remark;

	/**
	 * 状态 1：启用，2：停用
	 */
	private Integer status;

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
	 * 版本号
	 */
	private Integer version;

	/**
	* 舱位集合
	* */
	private List<Cabin> cabinList;
	
	/** 是否生成PNR: 0:否(默认); 1:是 */
	private Boolean advPnr;
	
	/** 是否全国政策: 0:否(默认); 1:是 */
	private Boolean isDefault;
	
	/** 批次编号, 外键 */
	private String batchNum;

	/**政策类型*/
	private String policyType;

	/**
	 * 是否需要审核
	 * 0:需要 1:不需要
	 */
	private Boolean isReview;
	
	/**
	 * 销售配置对应的供应商编号
	 *
	 */
	private String saleConfigWithNo;

	/**多个编号组成的字符串*/
	@Transient
	private String ids;
	
	public String getSaleConfigWithNo() {
		return saleConfigWithNo;
	}
	
	public void setSaleConfigWithNo(String saleConfigWithNo) {
		this.saleConfigWithNo = saleConfigWithNo;
	}
	
	public Boolean getReview() {
		return isReview;
	}
	
	public void setReview(Boolean review) {
		isReview = review;
	}
	
	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	private static final long serialVersionUID = 1L;

	public Boolean getqRebate() {

		return qRebate;
	}

	public void setqRebate(Boolean qRebate) {

		this.qRebate = qRebate;
	}

	public Boolean getsRebate() {

		return sRebate;
	}

	public void setsRebate(Boolean sRebate) {

		this.sRebate = sRebate;
	}

	public Boolean getYryqRebate() {

		return yryqRebate;
	}

	public void setYryqRebate(Boolean yryqRebate) {

		this.yryqRebate = yryqRebate;
	}

	public Long getPolicyNo() {

		return policyNo;
	}

	public void setPolicyNo(Long policyNo) {

		this.policyNo = policyNo;
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

	public String getAirline() {

		return airline;
	}

	public void setAirline(String airline) {

		this.airline = airline;
	}

	public String getDistributor() {

		return distributor;
	}

	public void setDistributor(String distributor) {

		this.distributor = distributor;
	}

	public String getProductor() {

		return productor;
	}

	public void setProductor(String productor) {

		this.productor = productor;
	}

	public String getSaleConfig() {

		return saleConfig;
	}

	public void setSaleConfig(String saleConfig) {

		this.saleConfig = saleConfig;
	}

	public String getProductNo() {

		return productNo;
	}

	public void setProductNo(String productNo) {

		this.productNo = productNo;
	}

	public String getTravellerType() {

		return travellerType;
	}

	public void setTravellerType(String travellerType) {

		this.travellerType = travellerType;
	}

	public Integer getTravellerLimit() {

		return travellerLimit;
	}

	public void setTravellerLimit(Integer travellerLimit) {

		this.travellerLimit = travellerLimit;
	}

	public Integer getTeamTicketType() {

		return teamTicketType;
	}

	public void setTeamTicketType(Integer teamTicketType) {

		this.teamTicketType = teamTicketType;
	}

	public Integer getTripType() {

		return tripType;
	}

	public void setTripType(Integer tripType) {

		this.tripType = tripType;
	}

	public String getGoStart() {

		return goStart;
	}

	public void setGoStart(String goStart) {

		this.goStart = goStart;
	}

	public String getExceptGoStart() {

		return exceptGoStart;
	}

	public void setExceptGoStart(String exceptGoStart) {

		this.exceptGoStart = exceptGoStart;
	}

	public String getGoEnd() {

		return goEnd;
	}

	public void setGoEnd(String goEnd) {

		this.goEnd = goEnd;
	}

	public String getExceptGoEnd() {

		return exceptGoEnd;
	}

	public void setExceptGoEnd(String exceptGoEnd) {

		this.exceptGoEnd = exceptGoEnd;
	}

	public String getBackEnd() {

		return backEnd;
	}

	public void setBackEnd(String backEnd) {

		this.backEnd = backEnd;
	}

	public String getExceptBackEnd() {

		return exceptBackEnd;
	}

	public void setExceptBackEnd(String exceptBackEnd) {

		this.exceptBackEnd = exceptBackEnd;
	}

	public Boolean getAllowCrossLineMatch() {

		return allowCrossLineMatch;
	}

	public void setAllowCrossLineMatch(Boolean allowCrossLineMatch) {

		this.allowCrossLineMatch = allowCrossLineMatch;
	}

	public String getTravelDate() {

		return travelDate;
	}

	public void setTravelDate(String travelDate) {

		this.travelDate = travelDate;
	}

	public String getBackDate() {

		return backDate;
	}

	public void setBackDate(String backDate) {

		this.backDate = backDate;
	}

	public String getTicketDate() {

		return ticketDate;
	}

	public void setTicketDate(String ticketDate) {

		this.ticketDate = ticketDate;
	}

	public Boolean getIsSameCarrier() {

		return isSameCarrier;
	}

	public void setIsSameCarrier(Boolean isSameCarrier) {

		this.isSameCarrier = isSameCarrier;
	}

	public Boolean getIsCodeShare() {

		return isCodeShare;
	}

	public void setIsCodeShare(Boolean isCodeShare) {

		this.isCodeShare = isCodeShare;
	}

	public String getShareAirline() {

		return shareAirline;
	}

	public void setShareAirline(String shareAirline) {

		this.shareAirline = shareAirline;
	}

	public Boolean getOnlyDirectFlight() {

		return onlyDirectFlight;
	}

	public void setOnlyDirectFlight(Boolean onlyDirectFlight) {

		this.onlyDirectFlight = onlyDirectFlight;
	}

	public String getGoNotFlight() {

		return goNotFlight;
	}

	public void setGoNotFlight(String goNotFlight) {

		this.goNotFlight = goNotFlight;
	}

	public String getGoMustFlight() {

		return goMustFlight;
	}

	public void setGoMustFlight(String goMustFlight) {

		this.goMustFlight = goMustFlight;
	}

	public String getBackNotFlight() {

		return backNotFlight;
	}

	public void setBackNotFlight(String backNotFlight) {

		this.backNotFlight = backNotFlight;
	}

	public String getBackMustFlight() {

		return backMustFlight;
	}

	public void setBackMustFlight(String backMustFlight) {

		this.backMustFlight = backMustFlight;
	}

	public String getGoFlightNo() {

		return goFlightNo;
	}

	public void setGoFlightNo(String goFlightNo) {

		this.goFlightNo = goFlightNo;
	}

	public String getBackFlightNo() {

		return backFlightNo;
	}

	public void setBackFlightNo(String backFlightNo) {

		this.backFlightNo = backFlightNo;
	}

	public String getExceptFlightNo() {

		return exceptFlightNo;
	}

	public void setExceptFlightNo(String exceptFlightNo) {

		this.exceptFlightNo = exceptFlightNo;
	}

	public BigDecimal getAgencyFee() {

		return agencyFee;
	}

	public void setAgencyFee(BigDecimal agencyFee) {

		this.agencyFee = agencyFee;
	}

	public String getPolicyEffectStart() {
		return policyEffectStart;
	}

	public void setPolicyEffectStart(String policyEffectStart) {
		this.policyEffectStart = policyEffectStart;
	}

	public String getPolicyEffectEnd() {
		return policyEffectEnd;
	}

	public void setPolicyEffectEnd(String policyEffectEnd) {
		this.policyEffectEnd = policyEffectEnd;
	}

	public String getRemark() {

		return remark;
	}

	public void setRemark(String remark) {

		this.remark = remark;
	}

	public Integer getStatus() {

		return status;
	}

	public void setStatus(Integer status) {

		this.status = status;
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

	public Integer getVersion() {

		return version;
	}

	public void setVersion(Integer version) {

		this.version = version;
	}

	public List<Cabin> getCabinList() {

		return cabinList;
	}

	public void setCabinList(List<Cabin> cabinList) {

		this.cabinList = cabinList;
	}

	public String getTicketWay() {
		return ticketWay;
	}

	public void setTicketWay(String ticketWay) {
		this.ticketWay = ticketWay;
	}

	public Boolean getAdvPnr() {
		return advPnr;
	}

	public void setAdvPnr(Boolean advPnr) {
		this.advPnr = advPnr;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public String getPolicyType() {
		return policyType;
	}

	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	public Boolean getSameCarrier() {
		return isSameCarrier;
	}

	public void setSameCarrier(Boolean sameCarrier) {
		isSameCarrier = sameCarrier;
	}

	public Boolean getCodeShare() {
		return isCodeShare;
	}

	public void setCodeShare(Boolean codeShare) {
		isCodeShare = codeShare;
	}

	public Boolean getDefault() {
		return isDefault;
	}

	public void setDefault(Boolean aDefault) {
		isDefault = aDefault;
	}

	public Boolean getIsReview() {
		return isReview;
	}

	public void setIsReview(Boolean isReview) {
		this.isReview = isReview;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
}
