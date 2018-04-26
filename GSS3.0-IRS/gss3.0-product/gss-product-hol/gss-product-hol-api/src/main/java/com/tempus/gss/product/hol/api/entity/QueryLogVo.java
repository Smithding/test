package com.tempus.gss.product.hol.api.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.IdType;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tempus.gss.product.hol.api.entity.vo.ProfitStatement;

/**
 *
 * 查询日志条件
 *
 */
public class QueryLogVo implements Serializable {


	/** 业务代码-- eg:ORDER,POLICY,USER...... */
	private String bizCode;
	
	/** 应用代码--目前默认为GSS3.0 */
	private String appCode;
	
	/** 日志创建时间--取系统时间 */
	private Date createTimeStart;
	
	private Date createTimeEnd;

	/** 业务号 eg: 订单号、政策ID...... */
	private String bizNo;
	
	/** 交易单号 */
	private String transationOrderNo;
	
	/** 日志标题 */
	private String title;
	
	/** 日志详细描述 */
	private String desc;
	
	/** 操作者IP */
	private String requestIp;
	
	/** 操作类型 */
	private String optType;
	
	/** 操作者姓名 */
	private String optName;
	
	/** 操作者登录名 */
	private String optLoginName;
	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getBizNo() {
		return bizNo;
	}

	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}

	public String getTransationOrderNo() {
		return transationOrderNo;
	}

	public void setTransationOrderNo(String transationOrderNo) {
		this.transationOrderNo = transationOrderNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	public String getOptType() {
		return optType;
	}

	public void setOptType(String optType) {
		this.optType = optType;
	}

	public String getOptName() {
		return optName;
	}

	public void setOptName(String optName) {
		this.optName = optName;
	}

	public String getOptLoginName() {
		return optLoginName;
	}

	public void setOptLoginName(String optLoginName) {
		this.optLoginName = optLoginName;
	}
	public Date getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}
}
