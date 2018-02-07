package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;

/**
 * Created by Administrator on 2016/10/19.
 */
public class PassengerChangePriceVo implements Serializable {


	/**
	 * 改签编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long passengerChangePriceNo;


	/**
	 * 数据归属单位
	 */
	private Integer owner;

	/**
	 * 销售改签单编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long saleChangeNo;

	/**
	 * 销售单编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long saleOrderNo;

	/**
	 * 乘客编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long passengerNo;

	/**
	 * 销售差价.
	 */
	private BigDecimal salePrice;

	/**
	 * 销售税费
	 */
	private BigDecimal saleTax;

	/**
	 * 销售手续费
	 */
	private BigDecimal saleBrokerage;

	/**
	 * 销售其他费用
	 */
	private BigDecimal saleRest;

	/**
	 * 采购差价
	 */
	private BigDecimal buyPrice;

	/**
	 * 采购税费
	 */
	private BigDecimal buyTax;

	/**
	 * 采购手续费
	 */
	private BigDecimal buyBrokerage;

	/**
	 * 采购其他费用
	 */
	private BigDecimal buyRest;

	/**
	 * 删除标志 0 无效 已删除 1 有效
	 */
	private Byte valid;

	/**
	 * 启用状态 1：启用，2：停用
	 */
	private String status;

	/**
	 * 操作人
	 */
	private String creator;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 操作人
	 */
	private String modifier;

	/**
	 * 操作时间
	 */
	private Date modifyTime;

	private static final long serialVersionUID = 1L;

	public Long getPassengerChangePriceNo() {

		return passengerChangePriceNo;
	}

	public void setPassengerChangePriceNo(Long passengerChangePriceNo) {

		this.passengerChangePriceNo = passengerChangePriceNo;
	}


	public Integer getOwner() {

		return owner;
	}

	public void setOwner(Integer owner) {

		this.owner = owner;
	}

	public Long getSaleChangeNo() {

		return saleChangeNo;
	}

	public void setSaleChangeNo(Long saleChangeNo) {

		this.saleChangeNo = saleChangeNo;
	}

	public Long getSaleOrderNo() {

		return saleOrderNo;
	}

	public void setSaleOrderNo(Long saleOrderNo) {

		this.saleOrderNo = saleOrderNo;
	}

	public Long getPassengerNo() {

		return passengerNo;
	}

	public void setPassengerNo(Long passengerNo) {

		this.passengerNo = passengerNo;
	}

	public BigDecimal getSalePrice() {

		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {

		this.salePrice = salePrice;
	}

	public BigDecimal getSaleTax() {

		return saleTax;
	}

	public void setSaleTax(BigDecimal saleTax) {

		this.saleTax = saleTax;
	}

	public BigDecimal getSaleBrokerage() {

		return saleBrokerage;
	}

	public void setSaleBrokerage(BigDecimal saleBrokerage) {

		this.saleBrokerage = saleBrokerage;
	}

	public BigDecimal getSaleRest() {

		return saleRest;
	}

	public void setSaleRest(BigDecimal saleRest) {

		this.saleRest = saleRest;
	}

	public BigDecimal getBuyPrice() {

		return buyPrice;
	}

	public void setBuyPrice(BigDecimal buyPrice) {

		this.buyPrice = buyPrice;
	}

	public BigDecimal getBuyTax() {

		return buyTax;
	}

	public void setBuyTax(BigDecimal buyTax) {

		this.buyTax = buyTax;
	}

	public BigDecimal getBuyBrokerage() {

		return buyBrokerage;
	}

	public void setBuyBrokerage(BigDecimal buyBrokerage) {

		this.buyBrokerage = buyBrokerage;
	}

	public BigDecimal getBuyRest() {

		return buyRest;
	}

	public void setBuyRest(BigDecimal buyRest) {

		this.buyRest = buyRest;
	}

	public Byte getValid() {

		return valid;
	}

	public void setValid(Byte valid) {

		this.valid = valid;
	}

	public String getStatus() {

		return status;
	}

	public void setStatus(String status) {

		this.status = status;
	}

	public String getCreator() {

		return creator;
	}

	public void setCreator(String creator) {

		this.creator = creator;
	}

	public Date getCreateTime() {

		return createTime;
	}

	public void setCreateTime(Date createTime) {

		this.createTime = createTime;
	}

	public String getModifier() {

		return modifier;
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

}
