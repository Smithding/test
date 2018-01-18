package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 某一时间段内发生对应类型变更的酒店id列表
 * @author kai.yang
 *
 */
public class ResourceProductIds implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 酒店 Id
	 */
	@JSONField(name = "ResId")
	private Long resId;
	/**
	 * 房型标示 Id
	 */
	@JSONField(name = "ProIds")
	private List<String> proIds;
	
	/**
	 * 酒店房型政策集合
	 */
	@JSONField(name = "ResourceProductInformations")
	private List<ResourceProductInformations> resourceProductInformations;

	public Long getResId() {
		return resId;
	}

	public void setResId(Long resId) {
		this.resId = resId;
	}

	public List<String> getProIds() {
		return proIds;
	}

	public void setProIds(List<String> proIds) {
		this.proIds = proIds;
	}

	public List<ResourceProductInformations> getResourceProductInformations() {
		return resourceProductInformations;
	}

	public void setResourceProductInformations(List<ResourceProductInformations> resourceProductInformations) {
		this.resourceProductInformations = resourceProductInformations;
	}
	
	
}
