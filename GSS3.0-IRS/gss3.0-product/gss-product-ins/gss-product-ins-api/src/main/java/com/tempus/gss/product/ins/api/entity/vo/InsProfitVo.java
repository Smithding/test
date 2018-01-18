package com.tempus.gss.product.ins.api.entity.vo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.IdType;

import com.baomidou.mybatisplus.annotations.TableId;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.tempus.gss.product.ins.api.entity.ProfitStatement;
import com.tempus.gss.util.JsonUtil;

/**
 *
 * 保险二级控润入参
 *
 */
public class InsProfitVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	/** 编号 */
	@TableId(type = IdType.AUTO)
	private Long profitNo;

	/** 归集单位 */
	private Integer owner;

	/** 控润渠道类型 */
	private Long customerTypeNo;

	/** 控润渠道名称 */
	private String customerTypeName;
	
	/** 价格方式  1 不控 2 固定 3 区间控 */
	private Integer priceType;

	/** 1 预付 2 现付 */
	private Integer payType;

	/** 控润模式 1.控点 2.控现 */
	private Integer profitMode;

	/** 上级编号 */
	private Long parentId;

	/** 启用状态 0 禁用 1 启用 */
	private String status;

	/** 备注 */
	private String remark;

	/** 是否可售 1 可售 0 不可售 */
	private Integer valid;

	/** 创建者 */
	private String creator;

	/** 创建时间 */
	private Date createTime;

	/** 修改人 */
	private String modifier;

	/** 修改时间 */
	private Date modifierTime;
	
	/*private List<ProfitPrice> profitPriceList; */
	
	private ProfitStatement rebateRule;

	/** 返现的控润规则 */
	private ProfitStatement returnCashRule;

	private Long insuranceNo;
	
	private String insuranceName;

	private String insuranceNos;
    //修改控润的开始时间
	private String modifierStartDate;
	public String getModifierStartDate() {
		return modifierStartDate;
	}

	public void setModifierStartDate(String modifierStartDate) {
		this.modifierStartDate = modifierStartDate;
	}

	public String getModifierEndDate() {
		return modifierEndDate;
	}

	public void setModifierEndDate(String modifierEndDate) {
		this.modifierEndDate = modifierEndDate;
	}

	//修改控润的结束时间
	private String modifierEndDate;
	public String getInsuranceNos() {
		return insuranceNos;
	}

	public void setInsuranceNos(String insuranceNos) {
		this.insuranceNos = insuranceNos;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProfitNo() {
		return this.profitNo;
	}

	public void setProfitNo(Long profitNo) {
		this.profitNo = profitNo;
	}

	public Integer getOwner() {
		return this.owner;
	}

	public void setOwner(Integer owner) {
		this.owner = owner;
	}

	public Long getCustomerTypeNo() {
		return this.customerTypeNo;
	}

	public void setCustomerTypeNo(Long customerTypeNo) {
		this.customerTypeNo = customerTypeNo;
	}

	public String getCustomerTypeName() {
		return this.customerTypeName;
	}

	public void setCustomerTypeName(String customerTypeName) {
		this.customerTypeName = customerTypeName;
	}

	public Integer getPriceType() {
		return this.priceType;
	}

	public void setPriceType(Integer priceType) {
		this.priceType = priceType;
	}

	public Integer getPayType() {
		return this.payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Integer getProfitMode() {
		return this.profitMode;
	}

	public void setProfitMode(Integer profitMode) {
		this.profitMode = profitMode;
	}

	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getValid() {
		return this.valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Date getModifierTime() {
		return this.modifierTime;
	}

	public void setModifierTime(Date modifierTime) {
		this.modifierTime = modifierTime;
	}

	/*public List<ProfitPrice> getProfitPriceList() {
		return profitPriceList;
	}

	public void setProfitPriceList(List<ProfitPrice> profitPriceList) {
		this.profitPriceList = profitPriceList;
	}*/
	
	/**
	 * 标准的getter返回json格式数据，保存到mybatis，并标注为json序列化忽略
	 */
	@JsonIgnore
	public String getRebateRule() {
		return JsonUtil.toJson(rebateRule);
	}

	/**
	 * 自定义返回对象本身的getter，用于json序列化
	 */
	@JsonGetter("rebateRule")
	public ProfitStatement getRebateRuleObj() {
		return rebateRule;
	}

	/**
	 * 标准的setter返回json格式数据，用于mybatis从数据库字段变为对象，并标注为json序列化忽略
	 */
	@JsonIgnore
	public void setRebateRule(String rebateRule) {
		this.rebateRule = JsonUtil.toBean(rebateRule, ProfitStatement.class);
	}

	/**
	 * 专门用于json反序列化的setter，入参为对象
	 */
	@JsonSetter("rebateRule")
	public void setRebateRule(ProfitStatement rebateRule) {
		this.rebateRule = rebateRule;
	}

	/**
	 * 标准的getter返回json格式数据，保存到mybatis，并标注为json序列化忽略
	 */
	@JsonIgnore
	public String getReturnCashRule() {
		return JsonUtil.toJson(returnCashRule);
	}

	/**
	 * 自定义返回对象本身的getter，用于json序列化
	 */
	@JsonGetter("returnCashRule")
	public ProfitStatement getReturnCashRuleObj() {
		return returnCashRule;
	}

	@JsonIgnore
	public void setReturnCashRule(String returnCashRule) {
		this.returnCashRule = JsonUtil.toBean(returnCashRule, ProfitStatement.class);
	}

	/**
	 * 专门用于json反序列化的setter，入参为对象
	 */
	@JsonSetter("returnCashRule")
	public void setReturnCashRule(ProfitStatement returnCashRule) {
		this.returnCashRule = returnCashRule;
	}

	public Long getInsuranceNo() {
		return insuranceNo;
	}

	public void setInsuranceNo(Long insuranceNo) {
		this.insuranceNo = insuranceNo;
	}

	public String getInsuranceName() {
		return insuranceName;
	}

	public void setInsuranceName(String insuranceName) {
		this.insuranceName = insuranceName;
	}
	
}
