package com.tempus.gss.product.hol.api.entity.vo;

import java.io.Serializable;
import java.util.List;

import com.tempus.gss.product.hol.api.entity.ProfitPrice;



/**
 *
 * 酒店供应商
 *
 */
public class CreateProfitVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 控润渠道类型 */
	private Long customerTypeNo;

	/** 控润渠道名称 */
	private String customerTypeName;

	/** 供应UBP编号 */
	private String supplierSource;

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

	private List<ProfitPrice> profitPriceList; 
	
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

	public String getSupplierSource() {
		return this.supplierSource;
	}

	public void setSupplierSource(String supplierSource) {
		this.supplierSource = supplierSource;
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

	public List<ProfitPrice> getProfitPriceList() {
		return profitPriceList;
	}

	public void setProfitPriceList(List<ProfitPrice> profitPriceList) {
		this.profitPriceList = profitPriceList;
	}
	
	
}
