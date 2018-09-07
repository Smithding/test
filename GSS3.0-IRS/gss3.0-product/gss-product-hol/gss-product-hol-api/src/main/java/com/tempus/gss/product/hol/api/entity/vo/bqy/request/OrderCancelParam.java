package com.tempus.gss.product.hol.api.entity.vo.bqy.request;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * 取消订单参数
 *
 */
public class OrderCancelParam extends BaseQueryParam {

	private static final long serialVersionUID = 1L;
	
	@JSONField(name="BookingUserId")
	private String bookingUserId;	//预订用户Id
	
	@JSONField(name="OrderNumber")
	private Long orderNumber;		//订单号

	@JSONField(name = "HotelOrderNumber")
	private Long hotelOrderNumber;	//订单号(退订使用)

	public String getBookingUserId() {
		return bookingUserId;
	}

	public void setBookingUserId(String bookingUserId) {
		this.bookingUserId = bookingUserId;
	}

	public Long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Long getHotelOrderNumber() {
		return hotelOrderNumber;
	}

	public void setHotelOrderNumber(Long hotelOrderNumber) {
		this.hotelOrderNumber = hotelOrderNumber;
	}
}
