package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * TC酒店房型集合【已废弃】
 * @author kai.yang
 *
 */
public class ResourceProductInfo implements Serializable{
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
	
	
	
	
}
