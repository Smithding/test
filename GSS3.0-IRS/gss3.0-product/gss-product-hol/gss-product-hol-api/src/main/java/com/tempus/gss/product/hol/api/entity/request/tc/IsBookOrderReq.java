package com.tempus.gss.product.hol.api.entity.request.tc;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 可订检查接口查询入参
 * @author kai.yang
 *
 */
public class IsBookOrderReq implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 酒店 id
	 */
	@JSONField(name = "ResId")
	private Long resId;
	/**
	 * 房型id
	 */
	@JSONField(name = "ProId")
	private Long proId;
	/**
	 * 政策 id
	 */
	@JSONField(name = "ProductUniqueId")
	private Long productUniqueId;
	/**
	 * 入住日期
	 */
	@JSONField(name = "ComeDate")
	private String comeDate;
	/**
	 * 到店时间
	 */
	@JSONField(name = "ComeTime")
	private String comeTime;
	/**
	 * 离店日期
	 */
	@JSONField(name = "LeaveDate")
	private String leaveDate;
	/**
	 * 预订房间数量
	 */
	@JSONField(name = "BookingCount")
	private Integer bookingCount;
	/**
	 * 客人数量入参，目前取的与房间数一致
	 */
	@JSONField(name = "GuestCount")
	private Integer guestCount;
	/**
	 * 客人选中的实际房间数
	 */
//	@JSONField(serialize = false)
//	private Integer factBookingCount;
	/**
	 * 实际客人总数量
	 */
//	@JSONField(serialize = false)
//	private Integer factGuestCount;
	
	public Long getResId() {
		return resId;
	}
	public void setResId(Long resId) {
		this.resId = resId;
	}
	public Long getProId() {
		return proId;
	}
	public void setProId(Long proId) {
		this.proId = proId;
	}
	public Long getProductUniqueId() {
		return productUniqueId;
	}
	public void setProductUniqueId(Long productUniqueId) {
		this.productUniqueId = productUniqueId;
	}
	public String getComeDate() {
		return comeDate;
	}
	public void setComeDate(String comeDate) {
		this.comeDate = comeDate;
	}
	public String getComeTime() {
		return comeTime;
	}
	public void setComeTime(String comeTime) {
		this.comeTime = comeTime;
	}
	public String getLeaveDate() {
		return leaveDate;
	}
	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}
	public Integer getBookingCount() {
		return bookingCount;
	}
	public void setBookingCount(Integer bookingCount) {
		this.bookingCount = bookingCount;
	}
	public Integer getGuestCount() {
		return guestCount;
	}
	public void setGuestCount(Integer guestCount) {
		this.guestCount = guestCount;
	}
//	public Integer getFactBookingCount() {
//		return factBookingCount;
//	}
//	public void setFactBookingCount(Integer factBookingCount) {
//		this.factBookingCount = factBookingCount;
//	}
//	public Integer getFactGuestCount() {
//		return factGuestCount;
//	}
//	public void setFactGuestCount(Integer factGuestCount) {
//		this.factGuestCount = factGuestCount;
//	}
}
