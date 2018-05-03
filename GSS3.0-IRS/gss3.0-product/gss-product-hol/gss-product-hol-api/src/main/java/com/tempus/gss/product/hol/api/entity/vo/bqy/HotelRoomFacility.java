package com.tempus.gss.product.hol.api.entity.vo.bqy;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * 酒店房间设施服务
 */
public class HotelRoomFacility implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@JSONField(name="ParentId")
	private int parentId;				//父级ID
	
	@JSONField(name="ParentName")
	private String parentName;			//父级名称
	
	private List<ChildRoomFacility> childFacility;		//子分类设施服务

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public List<ChildRoomFacility> getChildFacility() {
		return childFacility;
	}

	public void setChildFacility(List<ChildRoomFacility> childFacility) {
		this.childFacility = childFacility;
	}
	
}	
