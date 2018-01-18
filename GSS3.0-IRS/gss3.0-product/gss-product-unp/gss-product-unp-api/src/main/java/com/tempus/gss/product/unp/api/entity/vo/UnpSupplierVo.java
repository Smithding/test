/**
 * UnpSupplierVo.java
 * com.tempus.gss.product.unp.api.entity.vo
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2017年2月24日 		niepeng
 *
 * Copyright (c) 2017, TNT All Rights Reserved.
*/

package com.tempus.gss.product.unp.api.entity.vo;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * ClassName:UnpSupplierVo
 * Function: 通用产品供应商
 *
 * @author   niepeng
 * @version  
 * @since    Ver 1.1
 * @Date	 2017年2月24日		下午3:17:02
 *
 * @see 	 
 *  
 */
@JsonInclude(Include.NON_NULL)
@Alias("unpSupplierVo")
public class UnpSupplierVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	/** 供应商编号 */
	private Long supplierNo;

	/** 是否有效 */
	private Integer valid;

	/** 状态 */
	private Integer status;

	/** 支付账号 */
	private Long capitalAccountNo;

	/** 开户账号 */
	private Long accountNo;

	/** 创建人 */
	private String creator;

	/** 创建时间 */
	private Date createTime;

	/** 排序编号 */
	private Long sort;

	
	public Long getId() {
	
		return id;
	}

	
	public void setId(Long id) {
	
		this.id = id;
	}

	
	public Long getSupplierNo() {
	
		return supplierNo;
	}

	
	public void setSupplierNo(Long supplierNo) {
	
		this.supplierNo = supplierNo;
	}

	
	public Integer getValid() {
	
		return valid;
	}

	
	public void setValid(Integer valid) {
	
		this.valid = valid;
	}

	
	public Integer getStatus() {
	
		return status;
	}

	
	public void setStatus(Integer status) {
	
		this.status = status;
	}

	
	public Long getCapitalAccountNo() {
	
		return capitalAccountNo;
	}

	
	public void setCapitalAccountNo(Long capitalAccountNo) {
	
		this.capitalAccountNo = capitalAccountNo;
	}

	
	public Long getAccountNo() {
	
		return accountNo;
	}

	
	public void setAccountNo(Long accountNo) {
	
		this.accountNo = accountNo;
	}

	
	public String getCreator() {
	
		return creator;
	}

	
	public void setCreator(String creator) {
	
		this.creator = creator;
	}

	
	public Date getCreateTime() {
	
		return createTime;
	}

	
	public void setCreateTime(Date createTime) {
	
		this.createTime = createTime;
	}

	
	public Long getSort() {
	
		return sort;
	}

	
	public void setSort(Long sort) {
	
		this.sort = sort;
	}
}

