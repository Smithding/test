package com.tempus.gss.product.hol.api.entity.vo.bqy.response;

import java.io.Serializable;
import java.math.BigDecimal;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 酒店试预订返回结果
 */
public class BookOrderResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@JSONField(name="CheckPrice")
	private BigDecimal checkPrice;		//价格
	
	@JSONField(name="CheckInTime")
	private String checkInTime;			//入住时间
	
	@JSONField(name="CheckOutTime")
	private String checkOutTime;		//离店时间
	
	@JSONField(name="CanBook")
	private Boolean canBook;			//是否可预订
	
	@JSONField(name="AvailableQuantity")
	private int availableQuantity;		//剩余房间数量

	public BigDecimal getCheckPrice() {
		return checkPrice;
	}

	public void setCheckPrice(BigDecimal checkPrice) {
		this.checkPrice = checkPrice;
	}

	public String getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(String checkInTime) {
		this.checkInTime = checkInTime;
	}

	public String getCheckOutTime() {
		return checkOutTime;
	}

	public void setCheckOutTime(String checkOutTime) {
		this.checkOutTime = checkOutTime;
	}

	public Boolean getCanBook() {
		return canBook;
	}

	public void setCanBook(Boolean canBook) {
		this.canBook = canBook;
	}

	public int getAvailableQuantity() {
		return availableQuantity;
	}

	public void setAvailableQuantity(int availableQuantity) {
		this.availableQuantity = availableQuantity;
	}
	
}
