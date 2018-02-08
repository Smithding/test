package com.tempus.gss.product.hol.api.entity.request.tc;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * 查看订单详情入参
 * @author kai.yang
 *
 */
public class OrderDetailInfoReq implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 订单流水号/客户订单号
	 */
	@JSONField(name = "OrderId")
	private String orderId;
	/**
	 * 订单状态：默认状态-1，
	 * 待确认库存 0，
	 * 待支付 1，
	 * 已取消 2，
	 * 已支付 3，
	 * 待同程确认 5，
	 * 同程已确认10，
	 * 确认入住 11，
	 * 确认未住 12，
	 * 申请部分退款(有人出游)15，
	 * 申请全额退款 20，
	 * 全额退款结束 25，
	 * 部分退款结束 30，
	 * 已结算 35，
	 * 订单结束 40
	 */
	@JSONField(name = "OrderStatus")
	private Integer orderStatus;
	/**
	 * 房型标题
	 */
	@JSONField(name = "ProductTitle")
	private String productTitle;
	/**
	 * 查询开始时间，以出游开始日期为准，默认'1900-01-01 00:00:00'
	 */
	@JSONField(name = "StartTime")
	private String startTime;
	
	/**
	 * 查询结束时间，以出游开始日期为准，默认当前时间加一年
	 */
	@JSONField(name = "EndTime")
	private String endTime;
	
	/**
	 * 查询开始时间，以创建日期为准，默认'1900-01-01 00:00:00'
	 */
	@JSONField(name = "CreateStartTime")
	private String createStartTime;
	/**
	 * 查询结束时间，以创建日期为准，默认当前时间
	 */
	@JSONField(name = "CreateEndTime")
	private String createEndTime;
	/**
	 * 当前页码，默认值：1
	 */
	@JSONField(name = "PageIndex")
	private Integer pageIndex;
	/**
	 * 每页条数，默认值：10
	 */
	@JSONField(name = "PageSize")
	private Integer pageSize;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getProductTitle() {
		return productTitle;
	}
	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
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
	public String getCreateStartTime() {
		return createStartTime;
	}
	public void setCreateStartTime(String createStartTime) {
		this.createStartTime = createStartTime;
	}
	public String getCreateEndTime() {
		return createEndTime;
	}
	public void setCreateEndTime(String createEndTime) {
		this.createEndTime = createEndTime;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
}
