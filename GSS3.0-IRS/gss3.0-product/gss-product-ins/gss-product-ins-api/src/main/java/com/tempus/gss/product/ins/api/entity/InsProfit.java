package com.tempus.gss.product.ins.api.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;

/**
 * 保险二级控润实体类
 * @author kai.yang
 *
 */
@TableName("INS_PROFIT")
public class InsProfit implements Serializable {
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId(type = IdType.AUTO)
	private Long id;

	/** 编号 */
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
	
	private List<InsProfitPrice> profitPriceList;
	
	private ProfitStatement rebateRule;

	/** 返现的控润规则 */
	private ProfitStatement returnCashRule;
	/**
	 * 控润点数
	 */
	private BigDecimal profitCount;
	/**
	 * 控润价格
	 */
	private BigDecimal profitMoney;
	
	private Long insuranceNo;
	
	private String insuranceName;
	
	public Long getId() {
		return this.id;
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

	public List<InsProfitPrice> getProfitPriceList() {
		return profitPriceList;
	}

	public void setProfitPriceList(List<InsProfitPrice> profitPriceList) {
		this.profitPriceList = profitPriceList;
	}

	public ProfitStatement getRebateRule() {
		return rebateRule;
	}

	public void setRebateRule(ProfitStatement rebateRule) {
		this.rebateRule = rebateRule;
	}

	public ProfitStatement getReturnCashRule() {
		return returnCashRule;
	}

	public void setReturnCashRule(ProfitStatement returnCashRule) {
		this.returnCashRule = returnCashRule;
	}

	public BigDecimal getProfitCount() {
		return profitCount;
	}

	public void setProfitCount(BigDecimal profitCount) {
		this.profitCount = profitCount;
	}

	public BigDecimal getProfitMoney() {
		return profitMoney;
	}

	public void setProfitMoney(BigDecimal profitMoney) {
		this.profitMoney = profitMoney;
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
	
