package com.tempus.gss.product.hol.api.entity.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;


import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 *
 * 酒店供应商
 *
 */
@TableName("HOL_SUPPLIER")
public class SupplierVo implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** UBP编号 */
	private String GSSCode;
	
	private Long supplierNo;
	
	/** 归集单位 */
	private Long owner;

	/** 供应商编号 101:千淘 102:艺龙 */
	private String supplierCode;
	
	/**支付方式 */
	private Integer payType;

	/** 返点 */
	private BigDecimal rate;

	/**  */
	private String context;

	/**  */
	private Integer status;
	
	/** 备注,描述 */
	private String remark;

	/** 创建者, 默认:sys */
	private String creator;

	/** 创建时间 */
	private Date createTime;

	/** 修改者, 默认:sys */
	private String modifier;

	/** 修改时间 */
	private Date modifierTime;
	
	private List<ContextVo> contextVoList;


	/*public String getUBPCode() {
		return UBPCode;
	}

	public void setUBPCode(String uBPCode) {
		UBPCode = uBPCode;
	}*/
	
	
	public String getSupplierCode() {
		return supplierCode;
	}

	public String getGSSCode() {
		return GSSCode;
	}

	public void setGSSCode(String gSSCode) {
		GSSCode = gSSCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public BigDecimal getRate() {
		return this.rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String getContext() {
		return this.context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public List<ContextVo> getContextVoList() {
		return contextVoList;
	}

	public void setContextVoList(List<ContextVo> contextVoList) {
		this.contextVoList = contextVoList;
	}

	public Long getOwner() {
		return owner;
	}

	public void setOwner(Long owner) {
		this.owner = owner;
	}

	public Date getModifierTime() {
		return modifierTime;
	}

	public void setModifierTime(Date modifierTime) {
		this.modifierTime = modifierTime;
	}

	public Long getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(Long supplierNo) {
		this.supplierNo = supplierNo;
	}
	
	
}
