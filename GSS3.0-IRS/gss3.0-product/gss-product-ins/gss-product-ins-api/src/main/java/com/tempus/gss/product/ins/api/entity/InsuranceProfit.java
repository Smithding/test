package com.tempus.gss.product.ins.api.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class InsuranceProfit implements Serializable {
	/**
	 * 编号
	 */
private long profitNo;
/**
 * 归集单位
 */
private int owner;
/**
 * 控润渠道类型
 */
private long customerTypeNo;
/**
 * 控润渠道名称
 */
private String customerTypeName;
/**
 * 供应UBP编号
 */
private String supplierSource;
/**
 * 价格方式  1 不控 2 固定 3 区间控
 */
private int priceType;
/**
 * 控润模式 1.控点 2.控现
 */
private int profitMode;
/**
 * 上级编号
 */
private long parentId;
/**
 * 备注
 */
private String remark;
/**
 * 启用状态 0 禁用 1 启用
 */
private String status;
/**
 * 创建者
 */
private String creator;
/**
 * 创建时间
 */
private String createTime;
/**
 * 修改人
 */
private String Modifier;
/**
 * 修改时间
 */
private String ModifierTime;
/**
 * 是否可售 1 可售 0 不可售
 */
private int value;
/**
 * 产品编号
 */
private long insuranceNo;
/**
 * 控润点数
 */
private BigDecimal profitCount;
/**
 * 控润价格
 */
private BigDecimal profitMoney;
public long getProfitNo() {
	return profitNo;
}
public void setProfitNo(long profitNo) {
	this.profitNo = profitNo;
}
public int getOwner() {
	return owner;
}
public void setOwner(int owner) {
	this.owner = owner;
}
public long getCustomerTypeNo() {
	return customerTypeNo;
}
public void setCustomerTypeNo(long customerTypeNo) {
	this.customerTypeNo = customerTypeNo;
}
public String getCustomerTypeName() {
	return customerTypeName;
}
public void setCustomerTypeName(String customerTypeName) {
	this.customerTypeName = customerTypeName;
}
public String getSupplierSource() {
	return supplierSource;
}
public void setSupplierSource(String supplierSource) {
	this.supplierSource = supplierSource;
}
public int getPriceType() {
	return priceType;
}
public void setPriceType(int priceType) {
	this.priceType = priceType;
}
public int getProfitMode() {
	return profitMode;
}
public void setProfitMode(int profitMode) {
	this.profitMode = profitMode;
}
public long getParentId() {
	return parentId;
}
public void setParentId(long parentId) {
	this.parentId = parentId;
}
public String getRemark() {
	return remark;
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
	return creator;
}
public void setCreator(String creator) {
	this.creator = creator;
}
public String getCreateTime() {
	return createTime;
}
public void setCreateTime(String createTime) {
	this.createTime = createTime;
}
public String getModifier() {
	return Modifier;
}
public void setModifier(String modifier) {
	Modifier = modifier;
}
public String getModifierTime() {
	return ModifierTime;
}
public void setModifierTime(String modifierTime) {
	ModifierTime = modifierTime;
}
public int getValue() {
	return value;
}
public void setValue(int value) {
	this.value = value;
}
public long getInsuranceNo() {
	return insuranceNo;
}
public void setInsuranceNo(long insuranceNo) {
	this.insuranceNo = insuranceNo;
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
}
