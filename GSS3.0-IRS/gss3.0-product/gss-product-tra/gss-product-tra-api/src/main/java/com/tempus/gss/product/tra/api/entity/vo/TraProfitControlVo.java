package com.tempus.gss.product.tra.api.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TraProfitControlVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long profitControlNo;
	
	private Integer owner;
	
	private String insuranceNo;
	
	private Long customerTypeNo;
	
	private BigDecimal brokerage;
	
	private Date createTime;
	
	private Date modifyTime;
	
	private String creator;
	
	private String modifier;
	
	private Boolean valid;

	public Long getProfitControlNo() {
		return profitControlNo;
	}

	public void setProfitControlNo(Long profitControlNo) {
		this.profitControlNo = profitControlNo;
	}

	public Integer getOwner() {
		return owner;
	}

	public void setOwner(Integer owner) {
		this.owner = owner;
	}

	public String getInsuranceNo() {
		return insuranceNo;
	}

	public void setInsuranceNo(String insuranceNo) {
		this.insuranceNo = insuranceNo;
	}

	public Long getCustomerTypeNo() {
		return customerTypeNo;
	}

	public void setCustomerTypeNo(Long customerTypeNo) {
		this.customerTypeNo = customerTypeNo;
	}

	public BigDecimal getBrokerage() {
		return brokerage;
	}

	public void setBrokerage(BigDecimal brokerage) {
		this.brokerage = brokerage;
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
	
	
}
