/**
 * ProfitVO.java
 * com.tempus.gss.product.ift.api.entity.vo
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2017年9月7日 		shuo.cheng
 *
 * Copyright (c) 2017, TNT All Rights Reserved.
*/

package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;

/**
 * ClassName:ProfitVO
 *
 * @author   shuo.cheng
 * @version  
 * @since    Ver 1.1
 * @Date	 2017年9月7日		上午10:21:07
 *
 * @see 	 
 *  
 */
public class ProfitVO implements Serializable {

	/**
	 * serialVersionUID:序列ID
	 *
	 * @since Ver 1.1
	 */

	private static final long serialVersionUID = -7977666601494598377L;

	/**
	 * 控润规则编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long profitNo;

	/**
	 * 编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long id;

	/**
	 * 客户编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long customerNo;

	/**
	 * 返点
	 */
	private BigDecimal rebate;

	/**
	 * 加价
	 */
	private BigDecimal raisePrice;

	/**
	 * 账号
	 */
	private String account;
	/**
	 * 控润渠道类型
	 */
	private Long customerTypeNo;

	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 启用状态
	 */
	private String status;

	/**
	 * 价格方式
	 */
	private Integer priceType;


	private Integer owner;

	private String customerName;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Integer getPriceType() {
		return priceType;
	}

	public void setPriceType(Integer priceType) {
		this.priceType = priceType;
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

	public Long getCustomerTypeNo() {
		return customerTypeNo;
	}

	public void setCustomerTypeNo(Long customerTypeNo) {
		this.customerTypeNo = customerTypeNo;
	}

	public Long getProfitNo() {

		return profitNo;
	}

	public void setProfitNo(Long profitNo) {

		this.profitNo = profitNo;
	}

	public Long getId() {

		return id;
	}

	public void setId(Long id) {

		this.id = id;
	}

	public Long getCustomerNo() {

		return customerNo;
	}

	public void setCustomerNo(Long customerNo) {

		this.customerNo = customerNo;
	}

	public BigDecimal getRebate() {

		return rebate;
	}

	public void setRebate(BigDecimal rebate) {

		this.rebate = rebate;
	}

	public BigDecimal getRaisePrice() {

		return raisePrice;
	}

	public void setRaisePrice(BigDecimal raisePrice) {

		this.raisePrice = raisePrice;
	}

	public String getAccount() {

		return account;
	}

	public void setAccount(String account) {

		this.account = account;
	}

	public Integer getOwner() {
		return owner;
	}

	public void setOwner(Integer owner) {
		this.owner = owner;
	}
}

