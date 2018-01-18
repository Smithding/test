package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * 支持的信用卡信息
 * @author kai.yang
 *
 */
public class SupportCardInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 卡类别
	 */
	@JSONField(name = "CatalogName")
	private String catalogName;
	/**
	 * 卡编码
	 */
	@JSONField(name = "CatalogCode")
	private String catalogCode;
	/**
	 * 银行卡信息
	 */
	@JSONField(name = "PayMentMerchants")
	private List<PaymentWay> payMentMerchants;
	
	public String getCatalogName() {
		return catalogName;
	}
	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}
	public String getCatalogCode() {
		return catalogCode;
	}
	public void setCatalogCode(String catalogCode) {
		this.catalogCode = catalogCode;
	}
	public List<PaymentWay> getPayMentMerchants() {
		return payMentMerchants;
	}
	public void setPayMentMerchants(List<PaymentWay> payMentMerchants) {
		this.payMentMerchants = payMentMerchants;
	}
	
}
