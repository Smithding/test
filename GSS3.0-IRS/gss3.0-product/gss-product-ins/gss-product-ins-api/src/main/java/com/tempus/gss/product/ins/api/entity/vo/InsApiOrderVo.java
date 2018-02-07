/**
 * SaleOrderVo.java
 * com.tempus.gss.product.ins.api.entity.vo
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2016年10月19日 		shuo.cheng
 *
 * Copyright (c) 2016, TNT All Rights Reserved.
*/

package com.tempus.gss.product.ins.api.entity.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName:InsApiOrderVo
 *
 * @author   Fengjie.luo
 * @version  
 * @since    Ver 1.1
 * @Date	 2017年02月20日		上午10:40:37
 *
 * @see 	 
 *  
 */
public class InsApiOrderVo extends InsApiBase implements Serializable {
	/**
	 * 订单编号
	 */
	private Long saleOrderNo;

	/**
	 * 保单号
	 */
	private String policyNo;

	/**
	 * 投保日期 yyyy-MM-dd HH:mm:ss
	 */
	private Date issueDate;

	/**
	 * 投保人姓名
	 */
	private String holderName;

	/**
	 * 险种
	 */
	private String insureType;

	/**
	 * 被保人姓名
	 */
	private String insuredName;

	/**
	 * 支付状态
	 */
	private String payStatus;

	public Long getSaleOrderNo() {
		return saleOrderNo;
	}

	public void setSaleOrderNo(Long saleOrderNo) {
		this.saleOrderNo = saleOrderNo;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	public String getInsureType() {
		return insureType;
	}

	public void setInsureType(String insureType) {
		this.insureType = insureType;
	}

	public String getInsuredName() {
		return insuredName;
	}

	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
}

