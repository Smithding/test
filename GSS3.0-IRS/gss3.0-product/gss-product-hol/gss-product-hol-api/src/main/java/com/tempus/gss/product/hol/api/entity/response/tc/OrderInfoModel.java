package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.tempus.gss.product.hol.api.util.DateUtil;

/**
 * 同程订单详细信息实体类
 * @author kai.yang
 *
 */
public class OrderInfoModel implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 客户订单号
	 */
	@JSONField(name = "CusOrderId")
	private String cusOrderId;
	/**
	 * 订单流水号
	 */
	@JSONField(name = "DisplayId")
	private String displayId;
	/**
	 * 订单状态：文字描述
	 */
	@JSONField(name = "OrderFlag")
	private String orderFlag;
	/**
	 * 订单状态枚举值：
	 * 待确认库存 0，
	 * 待支付 1，
	 * 已取消 2，
	 * 已支付 3，
	 * 待同程确认 5，
	 * 同程已确认 10，
	 * 确认入住11，
	 * 确认未住 12，
	 * 申请部分退款(有人出游)15，
	 * 申请全额退款 20，
	 * 全额退款结束 25，
	 * 部分退款结束 30，
	 * 已结算 35，
	 * 订单结束 40
	 */
	@JSONField(name = "OrderStatus")
	private String orderStatus;
	
	/**
	 * 总价 (订单+保险)
	 */
	@JSONField(name = "Origin")
	private BigDecimal origin;
	/**
	 * 保险金额
	 */
	@JSONField(name = "InsuranceAmount")
	private BigDecimal insuranceAmount;
	/**
	 * 订单金额
	 */
	@JSONField(name = "OrderAmount")
	private BigDecimal orderAmount;
	/**
	 * 客人已支付金额
	 */
	@JSONField(name = "Money")
	private BigDecimal money;
	/**
	 * 退款金额
	 */
	@JSONField(name = "RefundAmount")
	private BigDecimal refundAmount;
	/**
	 * 赔款金额
	 */
	@JSONField(name = "CompensateAmount")
	private BigDecimal compensateAmount;
	/**
	 * 手续费
	 */
	@JSONField(name = "PenaltyAmount")
	private BigDecimal penaltyAmount;
	/**
	 * 联系人
	 */
	@JSONField(name = "Contact")
	private String contact;
	/**
	 * 联系人手机
	 */
	@JSONField(name = "Cellphone")
	private String cellphone;
	/**
	 * 联系人性别
	 */
	@JSONField(name = "ContactSex")
	private Integer contactSex;
	/**
	 * 消费开始时间
	 */
	@JSONField(name = "StartTime")
	private String startTime;
	/**
	 * 消费结束时间
	 */
	@JSONField(name = "EndTime")
	private String endTime;
	/**
	 * 下单时间
	 */
	@JSONField(name = "CreateTime")
	private String createTime;
	/**
	 * 房型标题
	 */
	@JSONField(name = "ProductTitle")
	private String productTitle;
	
	/**
	 * 数量
	 */
	@JSONField(name = "Counts")
	private CountModel counts;
	
	/**
	 * 出游人信息集合
	 */
	@JSONField(name = "Passengers")
	private List<PassengerModel> passengers;
	/**
	 * 房型信息
	 */
	@JSONField(name = "Resources")
	private List<ResourceModel> resources;
	/**
	 * 最晚取消时间
	 */
	@JSONField(name = "LasestCancelTime")
	private String lasestCancelTime;
	/**
	 * 订单预订模式（默认值 0：待同程确认/审核后支付，1：直接支付，2：前台支付；3：担保）
	 */
	@JSONField(name = "PaymentFlag")
	private Integer paymentFlag;
	public String getCusOrderId() {
		return cusOrderId;
	}
	public void setCusOrderId(String cusOrderId) {
		this.cusOrderId = cusOrderId;
	}
	public String getDisplayId() {
		return displayId;
	}
	public void setDisplayId(String displayId) {
		this.displayId = displayId;
	}
	public String getOrderFlag() {
		return orderFlag;
	}
	public void setOrderFlag(String orderFlag) {
		this.orderFlag = orderFlag;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public BigDecimal getOrigin() {
		return origin;
	}
	public void setOrigin(BigDecimal origin) {
		this.origin = origin;
	}
	public BigDecimal getInsuranceAmount() {
		return insuranceAmount;
	}
	public void setInsuranceAmount(BigDecimal insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}
	public BigDecimal getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public BigDecimal getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}
	public BigDecimal getCompensateAmount() {
		return compensateAmount;
	}
	public void setCompensateAmount(BigDecimal compensateAmount) {
		this.compensateAmount = compensateAmount;
	}
	public BigDecimal getPenaltyAmount() {
		return penaltyAmount;
	}
	public void setPenaltyAmount(BigDecimal penaltyAmount) {
		this.penaltyAmount = penaltyAmount;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public Integer getContactSex() {
		return contactSex;
	}
	public void setContactSex(Integer contactSex) {
		this.contactSex = contactSex;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		startTime= DateUtil.stringToStrDate(startTime);
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		endTime= DateUtil.stringToStrDate(endTime);
		this.endTime = endTime;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		createTime= DateUtil.stringToStrDate(createTime);
		this.createTime = createTime;
	}
	public String getProductTitle() {
		return productTitle;
	}
	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}
	public CountModel getCounts() {
		return counts;
	}
	public void setCounts(CountModel counts) {
		this.counts = counts;
	}
	public List<PassengerModel> getPassengers() {
		return passengers;
	}
	public void setPassengers(List<PassengerModel> passengers) {
		this.passengers = passengers;
	}
	public List<ResourceModel> getResources() {
		return resources;
	}
	public void setResources(List<ResourceModel> resources) {
		this.resources = resources;
	}
	public String getLasestCancelTime() {
		return lasestCancelTime;
	}
	public void setLasestCancelTime(String lasestCancelTime) {
		this.lasestCancelTime = lasestCancelTime;
	}
	public Integer getPaymentFlag() {
		return paymentFlag;
	}
	public void setPaymentFlag(Integer paymentFlag) {
		this.paymentFlag = paymentFlag;
	}
	
	
}
