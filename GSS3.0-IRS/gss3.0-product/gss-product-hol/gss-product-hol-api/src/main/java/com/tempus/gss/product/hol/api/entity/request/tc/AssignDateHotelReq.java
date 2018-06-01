package com.tempus.gss.product.hol.api.entity.request.tc;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 获取同程某一时间段/某个月的酒店价格和库存信息入参
 * @author kai.yang
 *
 */
public class AssignDateHotelReq implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 酒店 Id
	 */
	@JSONField(name = "ResId")
	private Long resId;
	/**
	 * 房型 Id ，不支持多个
	 */
	@JSONField(name = "ProId")
	private String proId;
	/**
	 * 年份
	 */
	@JSONField(name = "Year")
	private Integer year;
	/**
	 * 月份
	 */
	@JSONField(name = "Month")
	private Integer month;
	/**
	 * 开始日期，若不使用请赋值“1900-1-1”
	 */
	@JSONField(name = "StartTime")
	private String startTime;
	/**
	 * 结束日期，若不使用请赋值“1900-1-1”
	 */
	@JSONField(name = "EndTime")
	private String endTime;
	/**
	 * 销售策略 Id(请求单个房型政策，需要填写)
	 */
	@JSONField(name = "ProductUniqueId")
	private String productUniqueId;
	/**
	 * 是否需要输出特殊策略的日期
	 * 0:不输出
	 * 1:输出 
	 * 默认:0 不输出
	 */
	@JSONField(name = "NeedSpecialPolicy")
	private Integer needSpecialPolicy;
	/**
	 * 政策来源（0：默认值，1：自签，2：携程，4：艺龙，8：Switch） -1:全部输出
	 */
	@JSONField(name = "SourceFrom")
	private String sourceFrom;
	/**
	 * 价格计划 id（新增参数，一个 RatePlanId对应多个 ProductUniqueId）
	 */
	@JSONField(name = "RatePlanId")
	private Long ratePlanId;
	
	/**
	 * 子酒店编码
	 */
	@JSONField(name = "HotelCode")
	private String hotelCode;
	/**
	 * 销售房型 id（子房型 id）
	 */
	@JSONField(name = "RoomTypeId")
	private String roomTypeId;
	
	public Long getResId() {
		return resId;
	}
	public void setResId(Long resId) {
		this.resId = resId;
	}
	public String getProId() {
		return proId;
	}
	public void setProId(String proId) {
		this.proId = proId;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getProductUniqueId() {
		return productUniqueId;
	}
	public void setProductUniqueId(String productUniqueId) {
		this.productUniqueId = productUniqueId;
	}
	public Integer getNeedSpecialPolicy() {
		return needSpecialPolicy;
	}
	public void setNeedSpecialPolicy(Integer needSpecialPolicy) {
		this.needSpecialPolicy = needSpecialPolicy;
	}
	public String getSourceFrom() {
		return sourceFrom;
	}
	public void setSourceFrom(String sourceFrom) {
		this.sourceFrom = sourceFrom;
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
