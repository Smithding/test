package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 全量酒店列表实体化
 * @author kai.yang
 *
 */
public class AllHotelList implements Serializable{
	private static final long serialVersionUID = 1L;
	@JSONField(name = "ResInfoList")
	private List<ResInfoList> resInfoList;
	
	@JSONField(name = "TotalCount")
	private Integer totalCount;
	
	public List<ResInfoList> getResInfoList() {
		return resInfoList;
	}
	public void setResInfoList(List<ResInfoList> resInfoList) {
		this.resInfoList = resInfoList;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	
}
