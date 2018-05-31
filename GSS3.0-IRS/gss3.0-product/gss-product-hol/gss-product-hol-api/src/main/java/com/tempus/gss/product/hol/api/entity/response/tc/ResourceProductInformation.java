package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * TC酒店房型政策集合
 * @author kai.yang
 *
 */
public class ResourceProductInformation implements Serializable{
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
	private String productUniqueId;
	/**
	 * 价 格 计 划 id （ 新 增 参 数 ， 一 个RatePlanId 对应多个 ProductUniqueId）
	 */
	@JSONField(name = "RatePlanId")
	private Long ratePlanId;
	/**
	 * 子酒店编码
	 */
	@JSONField(name = "HotelCode")
	private String hotelCode;
	
	/**
	 * 销售房型 ID（子房型 ID）
	 */
	@JSONField(name = "RoomTypeId")
	private String roomTypeId;
	
	public Long getResourceProductId() {
		return resourceProductId;
	}
	public void setResourceProductId(Long resourceProductId) {
		this.resourceProductId = resourceProductId;
	}
	
	public String getProductUniqueId() {
		return productUniqueId;
	}
	public void setProductUniqueId(String productUniqueId) {
		this.productUniqueId = productUniqueId;
	}
	public Long getRatePlanId() {
		return ratePlanId;
	}
	public void setRatePlanId(Long ratePlanId) {
		this.ratePlanId = ratePlanId;
	}
	public String getHotelCode() {
		return hotelCode;
	}
	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}
	public String getRoomTypeId() {
		return roomTypeId;
	}
	public void setRoomTypeId(String roomTypeId) {
		this.roomTypeId = roomTypeId;
	}
	
}
