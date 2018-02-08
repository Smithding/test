package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * 银行卡信息获取支持的信用卡信息
 * @author kai.yang
 *
 */
public class PaymentWay implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 卡类型
	 */
	@JSONField(name = "CaeditCardType")
	private String caeditCardType;
	/**
	 * 卡名称
	 */
	@JSONField(name = "PaymentWayName")
	private String paymentWayName;
	/**
	 * 卡描述
	 */
	@JSONField(name = "PaymentWayGlobalName")
	private String paymentWayGlobalName;
	/**
	 * 卡名
	 */
	@JSONField(name = "PaySystemName")
	private String paySystemName;
	/**
	 * 是否需要证件类型
	 */
	@JSONField(name = "IsNeedIdType")
	private Boolean isNeedIdType;
	/**
	 * 是否需要证件号码
	 */
	@JSONField(name = "IsNeedIdNumber")
	private Boolean isNeedIdNumber;
	/**
	 * 是否需要持卡人
	 */
	@JSONField(name = "IsNeedCardHolder")
	private Boolean isNeedCardHolder;
	/**
	 * 是否需要 CVV2
	 */
	@JSONField(name = "IsNeedVerifyNo")
	private Boolean isNeedVerifyNo;
	/**
	 * 是否需要手机号
	 */
	@JSONField(name = "IsNeedPhoneNo")
	private Boolean isNeedPhoneNo;
	/**
	 * 银行卡卡种
	 */
	@JSONField(name = "CreditCardBankId")
	private String creditCardBankId;
	/**
	 * 是否开放预售权
	 */
	@JSONField(name = "IsSupportPreAuth")
	private Boolean isSupportPreAuth;
	/**
	 * 银行卡 logo
	 */
	@JSONField(name = "CardBankLogoUrl")
	private String cardBankLogoUrl;
	
	
	/**
	 * 排序
	 */
	@JSONField(name = "OrderBy")
	private String orderBy;
	
	public String getCaeditCardType() {
		return caeditCardType;
	}
	public void setCaeditCardType(String caeditCardType) {
		this.caeditCardType = caeditCardType;
	}
	public String getPaymentWayName() {
		return paymentWayName;
	}
	public void setPaymentWayName(String paymentWayName) {
		this.paymentWayName = paymentWayName;
	}
	public String getPaymentWayGlobalName() {
		return paymentWayGlobalName;
	}
	public void setPaymentWayGlobalName(String paymentWayGlobalName) {
		this.paymentWayGlobalName = paymentWayGlobalName;
	}
	public String getPaySystemName() {
		return paySystemName;
	}
	public void setPaySystemName(String paySystemName) {
		this.paySystemName = paySystemName;
	}
	public Boolean getIsNeedIdType() {
		return isNeedIdType;
	}
	public void setIsNeedIdType(Boolean isNeedIdType) {
		this.isNeedIdType = isNeedIdType;
	}
	public Boolean getIsNeedIdNumber() {
		return isNeedIdNumber;
	}
	public void setIsNeedIdNumber(Boolean isNeedIdNumber) {
		this.isNeedIdNumber = isNeedIdNumber;
	}
	public Boolean getIsNeedCardHolder() {
		return isNeedCardHolder;
	}
	public void setIsNeedCardHolder(Boolean isNeedCardHolder) {
		this.isNeedCardHolder = isNeedCardHolder;
	}
	public Boolean getIsNeedVerifyNo() {
		return isNeedVerifyNo;
	}
	public void setIsNeedVerifyNo(Boolean isNeedVerifyNo) {
		this.isNeedVerifyNo = isNeedVerifyNo;
	}
	public Boolean getIsNeedPhoneNo() {
		return isNeedPhoneNo;
	}
	public void setIsNeedPhoneNo(Boolean isNeedPhoneNo) {
		this.isNeedPhoneNo = isNeedPhoneNo;
	}
	public String getCreditCardBankId() {
		return creditCardBankId;
	}
	public void setCreditCardBankId(String creditCardBankId) {
		this.creditCardBankId = creditCardBankId;
	}
	public Boolean getIsSupportPreAuth() {
		return isSupportPreAuth;
	}
	public void setIsSupportPreAuth(Boolean isSupportPreAuth) {
		this.isSupportPreAuth = isSupportPreAuth;
	}
	public String getCardBankLogoUrl() {
		return cardBankLogoUrl;
	}
	public void setCardBankLogoUrl(String cardBankLogoUrl) {
		this.cardBankLogoUrl = cardBankLogoUrl;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
}
