package com.tempus.gss.product.ins.api.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;

public class InsProfitPrice implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** ID, 物理主键 */
	@TableId(type = IdType.AUTO)
	private Long id;

	/** 归集单位 */
	private Long owner;

	/** 编号 */
	private Long priceNo;
	
	private Long profitNo;
	/** 区间开始值 */
	private BigDecimal priceFrom;

	/** 区间结束值 */
	private BigDecimal priceTo;

	/** 变动值 */
	private BigDecimal rate;

	/** 备注,描述 */
	private String remark;

	/** 状态 */
	private String status;

	/** 创建者, 默认:sys */
	private String creator;

	/** 创建时间 */
	private Date createTime;

	/** 修改者, 默认:sys */
	private String modifier;

	/** 修改时间 */
	private Date modifyTime;
	
	private Integer priceType;

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

	public Long getPriceNo() {
		return this.priceNo;
	}

	public void setPriceNo(Long priceNo) {
		this.priceNo = priceNo;
	}

	public BigDecimal getPriceFrom() {
		return this.priceFrom;
	}

	public void setPriceFrom(BigDecimal priceFrom) {
		this.priceFrom = priceFrom;
	}

	public BigDecimal getPriceTo() {
		return this.priceTo;
	}

	public void setPriceTo(BigDecimal priceTo) {
		this.priceTo = priceTo;
	}

	public BigDecimal getRate() {
		return this.rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String getRemark() {
		return this.remark;
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

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getValid() {
		return this.valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}
	
	public Integer getPriceType() {
		return priceType;
	}

	public void setPriceType(Integer priceType) {
		this.priceType = priceType;
	}

	public Long getProfitNo() {
		return profitNo;
	}

	public void setProfitNo(Long profitNo) {
		this.profitNo = profitNo;
	}
	
}
