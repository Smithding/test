/**
 * WarnOrderVO.java
 * com.tempus.gss.product.ift.api.entity.vo
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2017年8月21日 		shuo.cheng
 *
 * Copyright (c) 2017, TNT All Rights Reserved.
*/
/**
 * WarnOrderVO.java
 * com.tempus.gss.product.ift.api.entity.vo
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2017年8月21日 shuo.cheng
 *
 * Copyright (c) 2017, TNT All Rights Reserved.
*/


package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName:WarnOrderVO
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   shuo.cheng
 * @version  
 * @since    Ver 1.1
 * @Date	 2017	2017年8月21日		下午2:53:09
 *
 * @see 	 
 *  
 */

public class WarnOrderVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** ID */
	private Long id;

	/** 工单号 */
	private String orderid;

	/** 用户编号 */
	private String userid;

	/**
	 * 提醒开始时间
	 */
	private String startTime;
	/**
	 * 提醒结束时间
	 */
	private String endTime;

	/** 提醒时间 */
	private String datetime;

	/** 0未处理 1已处理 */
	private String status;

	/** 备注 */
	private String remarks;

	/** 创建人 */
	private String creator;

	/** 创建时间 */
	private Date createTime;

	/** 操作人 默认为：sys */
	private String modifier;

	/** 修改时间 */
	private Date modifyTime;

	/** 删除标志 0 无效 已删除 1 有效 */
	private Integer valid;

	public Long getId() {

		return this.id;
	}

	public void setId(Long id) {

		this.id = id;
	}

	public String getOrderid() {

		return this.orderid;
	}

	public void setOrderid(String orderid) {

		this.orderid = orderid;
	}

	public String getUserid() {

		return this.userid;
	}

	public void setUserid(String userid) {

		this.userid = userid;
	}

	public String getStartTime() {

		return startTime;
	}

	public void setStartTime(String startTime) {

		this.startTime = startTime;
	}

	public String getEndTime() {

		return endTime;
	}

	public void setEndTime(String endTime) {

		this.endTime = endTime;
	}

	public String getDatetime() {

		return this.datetime;
	}

	public void setDatetime(String datetime) {

		this.datetime = datetime;
	}

	public String getStatus() {

		return this.status;
	}

	public void setStatus(String status) {

		this.status = status;
	}

	public String getRemarks() {

		return this.remarks;
	}

	public void setRemarks(String remarks) {

		this.remarks = remarks;
	}

	public String getCreator() {

		return this.creator;
	}

	public void setCreator(String creator) {

		this.creator = creator;
	}

	public Date getCreateTime() {

		return this.createTime;
	}

	public void setCreateTime(Date createTime) {

		this.createTime = createTime;
	}

	public String getModifier() {

		return this.modifier;
	}

	public void setModifier(String modifier) {

		this.modifier = modifier;
	}

	public Date getModifyTime() {

		return this.modifyTime;
	}

	public void setModifyTime(Date modifyTime) {

		this.modifyTime = modifyTime;
	}

	public Integer getValid() {

		return this.valid;
	}

	public void setValid(Integer valid) {

		this.valid = valid;
	}

}

