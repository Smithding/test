package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;
import java.util.Date;
import java.util.TreeMap;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * 房型详情信息集合
 * @author kai.yang
 *
 */
public class ProInfoDetail implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 主键ID 同销售策略 Id
	 */
	@JSONField(name = "Id")
	private Long id;
	
	/**
	 * 房型 Id 房型/票种标示 id
	 */
	@JSONField(name = "ProId")
	private String proId;
	/**
	 * 房型名称
	 */
	@JSONField(name = "RoomName")
	private String roomName;
	/**
	 * 房型提前预定天数
	 */
	@JSONField(name = "BeforehandBookingDay")
	private Integer beforehandBookingDay;
	/**
	 * 房型可预订时间 默认每天可以到23:59:59 为止
	 */
	@JSONField(name = "ReserveTime")
	private String reserveTime;
	/**
	 * 销售策略 Id
	 */
	@JSONField(name = "ProductUniqueId")
	private Long productUniqueId;
	/**
	 * 政策名称
	 */
	@JSONField(name = "PolicyName")
	private String policyName;
	/**
	 * 政策来源（0：默认值，1：自签，2：携程，4：艺龙，8：Switch）
	 */
	@JSONField(name = "SourceFrom")
	private Integer sourceFrom;
	/**
	 * 支付方式（0：全部，1：现付，2：预付）
	 */
	@JSONField(name = "PaymentType")
	private Integer paymentType;
	/**
	 * 房型剩余库存量,价格详情value 为日期所对应的房型剩余库存量,价格详情
	 */
	@JSONField(name = "ProSaleInfoDetails")
	private TreeMap<String, ProSaleInfoDetail> proSaleInfoDetails;
	/**
	 * 最严取消规则
	 */
	@JSONField(name = "CancelRulesStrict")
	private CancelRulesStrictInfo cancelRulesStrict;
	/**
	 * 连住天数
	 */
	@JSONField(name = "CheckInDays")
	private Integer checkInDays;
	/**
	 * 价格计划 id（新增参数，一个RatePlanId 对 应 多 个ProductUniqueId）
	 */
	@JSONField(name = "RatePlanId")
	private Long ratePlanId;
	/**
	 * 酒店id
	 */
	@JSONField(name = "ResId")
	private Long resId;
	/**
	 * 更新库存价格的时间
	 */
	private String updateInvenTime;
	/**
	 * 该房间是否下线，上线为1， 下线为0
	 */
	private Integer saleStatus;
	
	
	public String getProId() {
		return proId;
	}
	public void setProId(String proId) {
		this.proId = proId;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public Integer getBeforehandBookingDay() {
		return beforehandBookingDay;
	}
	public void setBeforehandBookingDay(Integer beforehandBookingDay) {
		this.beforehandBookingDay = beforehandBookingDay;
	}
	public String getReserveTime() {
		return reserveTime;
	}
	public void setReserveTime(String reserveTime) {
		this.reserveTime = reserveTime;
	}
	public Long getProductUniqueId() {
		return productUniqueId;
	}
	public void setProductUniqueId(Long productUniqueId) {
		this.productUniqueId = productUniqueId;
	}
	public String getPolicyName() {
		return policyName;
	}
	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}
	public Integer getSourceFrom() {
		return sourceFrom;
	}
	public void setSourceFrom(Integer sourceFrom) {
		this.sourceFrom = sourceFrom;
	}
	public Integer getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}
	
	public TreeMap<String, ProSaleInfoDetail> getProSaleInfoDetails() {
		return proSaleInfoDetails;
	}
	public void setProSaleInfoDetails(TreeMap<String, ProSaleInfoDetail> proSaleInfoDetails) {
		this.proSaleInfoDetails = proSaleInfoDetails;
	}
	public Long getResId() {
		return resId;
	}
	public void setResId(Long resId) {
		this.resId = resId;
	}
	public String getUpdateInvenTime() {
		return updateInvenTime;
	}
	public void setUpdateInvenTime(String updateInvenTime) {
		this.updateInvenTime = updateInvenTime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public CancelRulesStrictInfo getCancelRulesStrict() {
		return cancelRulesStrict;
	}
	public void setCancelRulesStrict(CancelRulesStrictInfo cancelRulesStrict) {
		this.cancelRulesStrict = cancelRulesStrict;
	}
	public Integer getCheckInDays() {
		return checkInDays;
	}
	public void setCheckInDays(Integer checkInDays) {
		this.checkInDays = checkInDays;
	}
	public Long getRatePlanId() {
		return ratePlanId;
	}
	public void setRatePlanId(Long ratePlanId) {
		this.ratePlanId = ratePlanId;
	}
	public Integer getSaleStatus() {
		return saleStatus;
	}
	public void setSaleStatus(Integer saleStatus) {
		this.saleStatus = saleStatus;
	}
	
}
