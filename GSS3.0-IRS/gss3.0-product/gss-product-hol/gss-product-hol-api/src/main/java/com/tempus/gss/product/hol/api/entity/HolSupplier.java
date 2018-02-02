package com.tempus.gss.product.hol.api.entity;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.IdType;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 *
 * 酒店供应商
 *
 */
@TableName("HOL_SUPPLIER")
public class HolSupplier implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** ID, 物理主键 */
	@TableId(type = IdType.AUTO)
	private Long id;

	/** 归集单位 */
	private Long owner;

	/** UBP编号 */
	@JSONField(name = "GSSCode")
	private String GSSCode;
	
	
	private Long supplierNo;

	/** 供应商编号 101:千淘 102:艺龙 */
	@JSONField(name = "SupplierCode ")
	private String supplierCode;
	
	/**支付方式 */
	private Integer payType;

	/** 返点 */
	@JSONField(name = "Rate")
	private BigDecimal rate;

	/**  */
	@JSONField(name = "Context")
	private String context;

	/** 备注,描述 */
	private String remark;

	/**  */
	private Integer status;

	/** 创建者, 默认:sys */
	private String creator;

	/** 创建时间 */
	private Date createTime;

	/** 修改者, 默认:sys */
	private String modifier;

	/** 修改时间 */
	private Date modifierTime;

	/** 有效, 0:无效,已删除; 1:有效,正常(默认) */
	private Integer valid;


	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOwner() {
		return this.owner;
	}

	public void setOwner(Long owner) {
		this.owner = owner;
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

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Date getModifierTime() {
		return modifierTime;
	}

	public void setModifierTime(Date modifierTime) {
		this.modifierTime = modifierTime;
	}

	public Integer getValid() {
		return this.valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

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

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Long getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(Long supplierNo) {
		this.supplierNo = supplierNo;
	}
	
	
}
