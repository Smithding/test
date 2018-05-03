package com.tempus.gss.product.hol.api.entity.vo.bqy;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 酒店设施服务
 */
public class HotelFacility implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@JSONField(name="ParentId")
	private String parentId;			//主分类ID
	
	@JSONField(name="ParentName")
	private String parentName;			//主分类名称
	
	@JSONField(name="ChildFacilityCode")
	private String childFacilityCode;	//子节点code
	
	@JSONField(name="Sort")
	private int sort;					//排序

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getChildFacilityCode() {
		return childFacilityCode;
	}

	public void setChildFacilityCode(String childFacilityCode) {
		this.childFacilityCode = childFacilityCode;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
}
