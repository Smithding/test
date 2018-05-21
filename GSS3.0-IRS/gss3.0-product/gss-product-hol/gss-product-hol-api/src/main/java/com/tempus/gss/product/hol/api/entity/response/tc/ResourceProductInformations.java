package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * TC酒店房型政策集合
 * @author kai.yang
 *
 */
public class ResourceProductInformations implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 房型 Id
	 */
	@JSONField(name = "ResourceProductId")
	private Long resourceProductId;
	/**
	 * 销售策略 Id
	 */
	@JSONField(name = "ProductUniqueId")
	private Long productUniqueId;
	/**
	 * 价 格 计 划 id （ 新 增 参 数 ， 一 个RatePlanId 对应多个 ProductUniqueId）
	 */
	@JSONField(name = "RatePlanId")
	private Long ratePlanId;
	
	public Long getResourceProductId() {
		return resourceProductId;
	}
	public void setResourceProductId(Long resourceProductId) {
		this.resourceProductId = resourceProductId;
	}
	public Long getProductUniqueId() {
		return productUniqueId;
	}
	public void setProductUniqueId(Long productUniqueId) {
		this.productUniqueId = productUniqueId;
	}
	public Long getRatePlanId() {
		return ratePlanId;
	}
	public void setRatePlanId(Long ratePlanId) {
		this.ratePlanId = ratePlanId;
	}
	
}
