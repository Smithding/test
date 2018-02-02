/**
 * OrderCancelVo.java
 * com.tempus.gss.product.ins.api.entity.vo
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2016年10月17日 		shuo.cheng
 *
 * Copyright (c) 2016, TNT All Rights Reserved.
*/

package com.tempus.gss.product.ins.api.entity.vo;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * ClassName:OrderCancelVo
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   shuo.cheng
 * @version  
 * @since    Ver 1.1
 * @Date	 2016年10月17日		下午5:32:45
 *
 * @see 	 
 *  
 */
@JsonInclude(Include.NON_NULL)
@Alias("insOrderCancelVo")
public class OrderCancelVo implements Serializable {

	/**
	 * serialVersionUID:序列ID
	 *
	 * @since Ver 1.1
	 */

	private static final long serialVersionUID = 7545787493028684435L;

	/**
	 * 保单号(限本地使用)
	 */
	private String policyNo;

	/**
	 * 保单号列表(调用退保接口时,传保单号使用此属性)
	 */
	@JsonIgnore
	private List<String> policyNoList;

	/**
	 * 退保原因
	 */
	private String quitReason;

	/**
	 * 渠道名称
	 */
	private String sourceName;

	/**
	 * 请求签名
	 */
	private String sign;

	public String getPolicyNo() {

		return policyNo;
	}

	public void setPolicyNo(String policyNo) {

		this.policyNo = policyNo;
	}

	public String getSourceName() {

		return sourceName;
	}

	public void setSourceName(String sourceName) {

		this.sourceName = sourceName;
	}

	public String getSign() {

		return sign;
	}

	public void setSign(String sign) {

		this.sign = sign;
	}

	public List<String> getPolicyNoList() {

		return policyNoList;
	}

	public void setPolicyNoList(List<String> policyNoList) {

		this.policyNoList = policyNoList;
	}

	public String getQuitReason() {

		return quitReason;
	}

	public void setQuitReason(String quitReason) {

		this.quitReason = quitReason;
	}

}

