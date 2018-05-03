package com.tempus.gss.product.hol.api.entity.vo.bqy;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 房间类型
 */
public class RoomType implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@JSONField(name="RoomTypeName")
	private String roomTypeName;		//房型名称
	
	@JSONField(name="StandardOccupancy")
	private String standardOccupancy;	//标准入住人数
	
	@JSONField(name="Size")
	private String size;				//床尺寸
	
	@JSONField(name="Floor")
	private String floor;				//楼高
	
	@JSONField(name="BedTypeCode")
	private String bedTypeCode;			//床型
	
	@JSONField(name="Quantity")
	private String quantity;			//房间数
	
	@JSONField(name="NonSmoking")
	private String nonSmoking;			//是否能吸烟
	
	@JSONField(name="RoomSize")
	private String roomSize;			//房间大小
	
	@JSONField(name="HasWindow")
	private String hasWindow;			//是否有窗
	
	@JSONField(name="RoomAmenities")
	private String roomAmenities;		//房间设施（是否有宽带）
	
	@JSONField(name="Features")
	private String 	features;			//客房信息
	
	@JSONField(name="Descriptive")
	private String 	descriptive;		//描述
	
	@JSONField(name="ProductId")
	private String productId;			//产品ID
	
	@JSONField(name="products")
	private List<Products> products;	//产品售卖规则

	public String getRoomTypeName() {
		return roomTypeName;
	}

	public void setRoomTypeName(String roomTypeName) {
		this.roomTypeName = roomTypeName;
	}

	public String getStandardOccupancy() {
		return standardOccupancy;
	}

	public void setStandardOccupancy(String standardOccupancy) {
		this.standardOccupancy = standardOccupancy;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getBedTypeCode() {
		return bedTypeCode;
	}

	public void setBedTypeCode(String bedTypeCode) {
		this.bedTypeCode = bedTypeCode;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getNonSmoking() {
		return nonSmoking;
	}

	public void setNonSmoking(String nonSmoking) {
		this.nonSmoking = nonSmoking;
	}

	public String getRoomSize() {
		return roomSize;
	}

	public void setRoomSize(String roomSize) {
		this.roomSize = roomSize;
	}

	public String getHasWindow() {
		return hasWindow;
	}

	public void setHasWindow(String hasWindow) {
		this.hasWindow = hasWindow;
	}

	public String getRoomAmenities() {
		return roomAmenities;
	}

	public void setRoomAmenities(String roomAmenities) {
		this.roomAmenities = roomAmenities;
	}

	public String getFeatures() {
		return features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	public String getDescriptive() {
		return descriptive;
	}

	public void setDescriptive(String descriptive) {
		this.descriptive = descriptive;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public List<Products> getProducts() {
		return products;
	}

	public void setProducts(List<Products> products) {
		this.products = products;
	}

}
