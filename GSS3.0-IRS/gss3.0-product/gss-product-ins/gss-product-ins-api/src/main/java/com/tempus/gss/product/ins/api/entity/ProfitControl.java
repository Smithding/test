package com.tempus.gss.product.ins.api.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;

/**
 * 保险控润..
 */
@Alias("insProfitControl")
public class ProfitControl implements Serializable {

	/**
	 * 控润编号
	 */
	private Long profitControlNo;

	/**
	 * 编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long id;

	/**
	 * 数据归属单位
	 */
	private Integer owner;

	/**
	 * 产品编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long insuranceNo;

	/**
	 * 控润渠道类型，目前可用范围：301（分销商），302（集团客户）,303（散客会员）,306（体内销售）
	 */
	private Long customerTypeNo;

	/**
	 * 销售价
	 */
	private BigDecimal salePrice;

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

	private static final long serialVersionUID = 1L;

	public Long getProfitControlNo() {

		return profitControlNo;
	}

	public void setProfitControlNo(Long profitControlNo) {

		this.profitControlNo = profitControlNo;
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

	public Long getInsuranceNo() {

		return insuranceNo;
	}

	public void setInsuranceNo(Long insuranceNo) {

		this.insuranceNo = insuranceNo;
	}

	public Long getCustomerTypeNo() {

		return customerTypeNo;
	}

	public void setCustomerTypeNo(Long customerTypeNo) {

		this.customerTypeNo = customerTypeNo;
	}

	public BigDecimal getSalePrice() {

		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {

		this.salePrice = salePrice;
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