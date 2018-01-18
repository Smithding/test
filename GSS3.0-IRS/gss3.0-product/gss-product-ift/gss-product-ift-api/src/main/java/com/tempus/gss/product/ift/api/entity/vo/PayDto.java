package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 支付的一些相关参数
 * @author hongqiaoxin
 *
 */
public class PayDto implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 业务单号
	 */
	private Long bussinessNo;

	/**
	 * 支付金额
	 */
	private BigDecimal amount;

	/**
	 * 产品主类型，１国内机票，２国际机票，３保险，４酒店，5机场服务，6票证，7其它，10火车票
	 */
	private String productType;

	/**
	 * 业务类型(1.交易单，2.销售单，3.采购单，4.销售变更单，5.采购变更单)
	 */
	private Integer businessType;

	/**
	 * 支付成功后回调的url
	 */
	private String successUrl;

	/**
	 * 支付类别代码（来源：IPayRestService.queryPayWay）
	 */
	private String categoryCode;

	/**
	 * 支付方式编号（来源：IPayRestService.queryPayWay）
	 */
	private Integer payWayCode;

	/**
	 * 支付账户编号
	 */
	private Long accountNo;
	
	/**
	 * 客户编号
	 */
	private Long customerNo;
	
	/**
	 * 客户类型
	 */
	private Long customerTypeNo;
	
	public Long getBussinessNo() {
		return bussinessNo;
	}

	public void setBussinessNo(Long bussinessNo) {
		this.bussinessNo = bussinessNo;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public String getSuccessUrl() {
		return successUrl;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public void setPayWayCode(Integer payWayCode) {
		this.payWayCode = payWayCode;
	}

	public Integer getPayWayCode() {
		return payWayCode;
	}

	public Long getAccountNo() {
		return accountNo;
	}
	
	public void setAccountNo(Long accountNo) {
		this.accountNo = accountNo;
	}
	
	public void setCustomerNo(Long customerNo) {
		this.customerNo = customerNo;
	}
	
	public Long getCustomerNo() {
		return customerNo;
	}
	
	public void setCustomerTypeNo(Long customerTypeNo) {
		this.customerTypeNo = customerTypeNo;
	}
	
	public Long getCustomerTypeNo() {
		return customerTypeNo;
	}
}
