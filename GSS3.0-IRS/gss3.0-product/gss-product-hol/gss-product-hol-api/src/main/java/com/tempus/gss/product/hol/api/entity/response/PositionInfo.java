package com.tempus.gss.product.hol.api.entity.response;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 热点信息
 * @author Administrator
 *
 */
public class PositionInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 热点编号
	 */
	@JSONField(name = "PositionID")
	private String positionID;
	
	/**
	 * 热点名称
	 */
	@JSONField(name = "PositionName")
	private String positionName;

	public String getPositionID() {
		return positionID;
	}

	public void setPositionID(String positionID) {
		this.positionID = positionID;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	
	
}
