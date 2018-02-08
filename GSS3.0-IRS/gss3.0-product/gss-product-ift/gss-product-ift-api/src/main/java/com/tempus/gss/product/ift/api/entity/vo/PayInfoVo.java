package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;

/**
 * <pre>
 * <b>采购单的支付信息.</b>
 * <b>Description:</b> 
 *    
 * <b>Author:</b> cz
 * <b>Date:</b> 2016年12月12日 17:02
 * <b>Copyright:</b> Copyright ©2016 tempus.cn. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2016年12月12日 17:02          cz
 *         new file.
 * </pre>
 */
public class PayInfoVo implements Serializable {

	/** 支付账号 */
	private String payAccount;
	
	/** 支付账号名称 */
	private String payName;
	
	/** 支付方式 */
	private Integer payWay;

	/** 支付类型（1 在线支付 2 帐期或代付 3 线下支付）*/
	private Integer payType;

	/**
	 * 构造方法
	 */
	public PayInfoVo() 
	{
		super();
	}
	
	/**
	 * 构造方法
	 * 
	 * @param payAccount 支付账号
	 * @param payName 支付账号名称
	 * @param payWay 支付方式
	 * @param payType 支付类型（1 在线支付 2 帐期或代付 3 线下支付）
	 */
	public PayInfoVo(String payAccount, String payName, Integer payWay, Integer payType)
	{
		this.payAccount = payAccount;
		this.payName = payName;
		this.payWay = payWay;
		this.payType = payType;
	}
	
	public String getPayAccount() {
		return payAccount;
	}

	public void setPayAccount(String payAccount) {
		this.payAccount = payAccount;
	}

	public String getPayName() {
		return payName;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}

	public Integer getPayWay() {
		return payWay;
	}

	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

}
