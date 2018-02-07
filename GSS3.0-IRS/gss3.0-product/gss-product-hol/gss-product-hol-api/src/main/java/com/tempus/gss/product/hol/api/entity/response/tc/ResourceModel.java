package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.tempus.gss.product.hol.api.util.DateUtil;

/**
 * 订单详情返回的房型信息
 * @author kai.yang
 *
 */
public class ResourceModel implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 酒店 ID
	 */
	@JSONField(name = "ResourceId")
	private Integer resourceId;
	/**
	 * 酒店名称
	 */
	@JSONField(name = "Name")
	private String name;
	/**
	 * 房型名称
	 */
	@JSONField(name = "ProductName")
	private String productName;
	/**
	 * 酒店类型：0 酒店，1 景区，2 餐饮，3门票
	 */
	@JSONField(name = "Type")
	private String type;
	/**
	 * 份数
	 */
	@JSONField(name = "PriceFraction")
	private Integer priceFraction;
	/**
	 * 酒店消费开始时间
	 */
	@JSONField(name = "UseStartDate")
	private String useStartDate;
	/**
	 * 酒店消费结束时间
	 */
	@JSONField(name = "UseEndDate")
	private String useEndDate;
	/**
	 * 备注/特殊要求
	 */
	@JSONField(name = "Remark")
	private String remark;
	/**
	 * 销售策略 Id
	 */
	@JSONField(name = "ProductUniqueId")
	private Integer productUniqueId;
	/**
	 * 供应商资源扩展信息，可存放供应商提供的资源额外非标准信息
	 * （比如：携程的酒店基本信息）
	 */
	@JSONField(name = "SupplierResourceProps")
	private String supplierResourceProps;
	/**
	 * 到店时间 只能是整点，默认 18 点，
	 * 超过 24 表示次日，如 25 为次日 1 点
	 */
	@JSONField(name = "ArrivalTime")
	private Integer arrivalTime;
	
	/**
	 * 供应商确认号（例如：携程的政策即为携程订单号）
	 */
	@JSONField(name = "SupplierConfirmNumber")
	private String supplierConfirmNumber;

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getPriceFraction() {
		return priceFraction;
	}

	public void setPriceFraction(Integer priceFraction) {
		this.priceFraction = priceFraction;
	}

	public String getUseStartDate() {
		return useStartDate;
	}

	public void setUseStartDate(String useStartDate) {
		useStartDate= DateUtil.stringToStrDate(useStartDate);
		this.useStartDate = useStartDate;
	}

	public String getUseEndDate() {
		return useEndDate;
	}

	public void setUseEndDate(String useEndDate) {
		useEndDate= DateUtil.stringToStrDate(useEndDate);
		this.useEndDate = useEndDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getProductUniqueId() {
		return productUniqueId;
	}

	public void setProductUniqueId(Integer productUniqueId) {
		this.productUniqueId = productUniqueId;
	}

	public String getSupplierResourceProps() {
		return supplierResourceProps;
	}

	public void setSupplierResourceProps(String supplierResourceProps) {
		this.supplierResourceProps = supplierResourceProps;
	}

	public Integer getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Integer arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getSupplierConfirmNumber() {
		return supplierConfirmNumber;
	}

	public void setSupplierConfirmNumber(String supplierConfirmNumber) {
		this.supplierConfirmNumber = supplierConfirmNumber;
	}
	
}
