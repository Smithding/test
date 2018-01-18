package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * 订单取消原因集合信息
 * @author kai.yang
 *
 */
public class CancelReasonModel implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键自增长 Id
	 */
	@JSONField(name = "Id")
	private Integer id;
	/**
	 * 原因类别名称
	 */
	@JSONField(name = "ReasonName")
	private String reasonName;
	/**
	 * 父级 Id（ZZY_OrderReason.Id)，为 0 是父级
	 */
	@JSONField(name = "ParentId")
	private Integer parentId;
	
	/**
	 * 排序值（值越大越靠前）
	 */
	@JSONField(name = "Sort")
	private Integer sort;
	/**
	 * 原因分类（2：酒店）
	 */
	@JSONField(name = "Category")
	private String category;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getReasonName() {
		return reasonName;
	}
	public void setReasonName(String reasonName) {
		this.reasonName = reasonName;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	
	
	
}
