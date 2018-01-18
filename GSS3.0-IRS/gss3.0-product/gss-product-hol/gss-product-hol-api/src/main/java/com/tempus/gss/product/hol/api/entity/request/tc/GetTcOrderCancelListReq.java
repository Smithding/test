package com.tempus.gss.product.hol.api.entity.request.tc;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * 获取同程订单取消原因集合
 * @author kai.yang
 *
 */
public class GetTcOrderCancelListReq implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 平台类型（6.分销平台
	 */
	@JSONField(name = "FromPlatment")
	private Integer fromPlatment;
	/**
	 * 原因分类（2：酒店)
	 */
	@JSONField(name = "Category")
	private Integer category;
	public Integer getFromPlatment() {
		return fromPlatment;
	}
	public void setFromPlatment(Integer fromPlatment) {
		this.fromPlatment = fromPlatment;
	}
	public Integer getCategory() {
		return category;
	}
	public void setCategory(Integer category) {
		this.category = category;
	}
	
	
	
	
}
