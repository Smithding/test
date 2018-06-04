package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class ResProBaseInfos implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 酒店ID
	 */
	@JSONField(name = "Id")
	private Long id;
	
	/**
	 * 酒店房型信息
	 */
	@JSONField(name = "ResProBaseInfos")
	private List<ResProBaseInfo> resProBaseInfos;
	
	private Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<ResProBaseInfo> getResProBaseInfos() {
		return resProBaseInfos;
	}

	public void setResProBaseInfos(List<ResProBaseInfo> resProBaseInfos) {
		this.resProBaseInfos = resProBaseInfos;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
