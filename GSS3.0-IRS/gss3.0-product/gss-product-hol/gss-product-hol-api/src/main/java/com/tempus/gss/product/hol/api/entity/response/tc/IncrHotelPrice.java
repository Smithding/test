package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 获取同程某一时间段内发生对应类型变更的酒店&及房型 id 列表接口
 * @author kai.yang
 *
 */
public class IncrHotelPrice implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 酒店 id 列表
	 */
	@JSONField(name = "ResourceProductIdsList")
	private List<ResourceProductIds> resourceProductIdsList;
	
	
	public List<ResourceProductIds> getResourceProductIdsList() {
		return resourceProductIdsList;
	}
	public void setResourceProductIdsList(List<ResourceProductIds> resourceProductIdsList) {
		this.resourceProductIdsList = resourceProductIdsList;
	}
}
