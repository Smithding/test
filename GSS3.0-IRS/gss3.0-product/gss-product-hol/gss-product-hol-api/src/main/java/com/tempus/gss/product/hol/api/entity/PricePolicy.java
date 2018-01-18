package com.tempus.gss.product.hol.api.entity;

import java.io.Serializable;
import java.util.Dictionary;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.tempus.gss.product.hol.api.entity.request.PriceFromTo;

public class PricePolicy implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 采购UBP编号 */
	@JSONField(name = "Clients")
	private Dictionary<String, String> clients;
	/** 真实供应商编号 */
	@JSONField(name = "Suppliers")
	private Dictionary<String, String> suppliers;
	/** 价格加价方式 1 不控 2 固定 3 区间控 */
	@JSONField(name = "PriceType")
	private Integer priceType;
	/** 1 预付 2 现付 */
	@JSONField(name = "PayType")
	private Integer payType;
	/** 运算规则*/
	@JSONField(name = "Prices")
	private List<PriceFromTo> prices;
	/** 供应UBP编号 */
	@JSONField(name = "SupplierSource")
	private String supplierSource;
	/** 价格策略编号 */
	@JSONField(name = "ID")
	private String id;
	/** 这个属性，只有是面付的时候才有效果，1 按金额返，0 按比例 */
	@JSONField(name = "IsAmount")
	private Integer isAmount;

	public Dictionary<String, String> getClients() {
		return clients;
	}

	public void setClients(Dictionary<String, String> clients) {
		this.clients = clients;
	}

	public Dictionary<String, String> getSuppliers() {
		return suppliers;
	}

	public void setSuppliers(Dictionary<String, String> suppliers) {
		this.suppliers = suppliers;
	}

	public Integer getPriceType() {
		return priceType;
	}

	public void setPriceType(Integer priceType) {
		this.priceType = priceType;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public List<PriceFromTo> getPrices() {
		return prices;
	}

	public void setPrices(List<PriceFromTo> prices) {
		this.prices = prices;
	}

	public String getSupplierSource() {
		return supplierSource;
	}

	public void setSupplierSource(String supplierSource) {
		this.supplierSource = supplierSource;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getIsAmount() {
		return isAmount;
	}

	public void setIsAmount(Integer isAmount) {
		this.isAmount = isAmount;
	}
	
	
}
