package com.tempus.gss.product.hol.api.entity.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.tempus.gss.order.entity.SaleOrder;
import com.tempus.gss.product.hol.api.entity.response.tc.CountModel;
import com.tempus.gss.product.hol.api.entity.response.tc.PassengerModel;
import com.tempus.gss.product.hol.api.entity.response.tc.ResourceModel;

public class TcOrderInfosDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
    /**
     * 数据归属单位
     */
    //@TableField(exist = false)
    private Integer owner;

    /**
     * 订单编号
     */
    private String hotelOrderNo;

    /**
     * 销售订单编号
     */
    private Long saleOrderNo;
    
    
    private SaleOrder saleOrder;
    
    /**
     * 采购订单编号
     */
    //@TableField(exist = false)
    private Long buyOrderNo;
    /**
     * 入住人姓名
     */
    private String guestName;
    /**
     * 入住人电话
     */
    private String guestMobile;
    /**
     * 价格合计
     */
    private BigDecimal totalPrice;

    /**
     * 返现合计
     */
    private BigDecimal totalRefund;
    /**
     * 最晚到店时间 yyyy-MM-dd HH:mm:ss
     */
    private String arriveHotelTime;
    /**
     * 预定时每晚的价格
     */
    private String eachNightPrice;
    /**
     * 担保类型
     */
    private String dbOrderType;
    /**
     * 担保金额
     */
    private BigDecimal dbOrderMoney;
    /**
     * 担保取消规则
     */
    private String dbCancelRule;
    /**
     * 最迟的取消时间，在这个时间前取消不需要扣除罚金
     */
    private Date cancelPenalty;
    /**
     * 最早到店限制时间（东八区北京时间）
     */
    private Date earlyArriveTime;
    /**
     * 最晚到店时间限制（东八区北京时间）
     */
    private Date latestArriveTime;
    /**
     * 特殊要求文本
     */
    private String requestText;
    /**
     * 特殊要求名称
     */
    private String requestName;
    /**
     * 晚数
     */
     private Integer nightCount;
    /**
     * 创建人
     */
    private String creator;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 修改时间
     */
    private Date modifyTime;
    
    /**
     * 银行id
     */
    private Long bankId;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 信用卡卡号
     */
    private String cardNumber;
    /**
     * 持卡人姓名
     */
    private String cardUserName;
    /**
     * 信用卡有效期
     */
    private String cardValueDate;
    /**
     * 证件名称
     */
    private String credentialName;
    /**
     * 证件号码
     */
    private String credentialNumber;
    /**
     * 持卡人预留手机号码
     */
    private String cardMobile;
    /**
     * 下单返回的信息
     */
    private String msg;
    /**
     * 下单完成后订单的金额
     */
    private BigDecimal orderMoney;
    /**
     * 订单是否可以及时确认,即库存确认
     */
    private Boolean isAffirm;
    /**
     * 返回码，0=>下单失败，1=>下单成功，2=>下单成功，支付失败，3=>下单成功，支付成功
     */
    private String resultCode;
    /**
 	 * 酒店住址
 	 */
 	private String hotelAddress;
 	/**
 	 * 酒店电话
 	 */
 	private String hotelPhone;
 	
 	/**
	 * 客户订单号
	 */
	private String cusOrderId;
	/**
	 * 订单流水号
	 */
	private String displayId;
	/**
	 * 订单状态：文字描述
	 */
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
	private String orderStatus;
	
	/**
	 * 总价 (订单+保险)
	 */
	private BigDecimal origin;
	/**
	 * 保险金额
	 */
	private BigDecimal insuranceAmount;
	/**
	 * 订单金额
	 */
	private BigDecimal orderAmount;
	/**
	 * 客人已支付金额
	 */
	private BigDecimal money;
	/**
	 * 退款金额
	 */
	private BigDecimal refundAmount;
	/**
	 * 赔款金额
	 */
	private BigDecimal compensateAmount;
	/**
	 * 手续费
	 */
	private BigDecimal penaltyAmount;
	/**
	 * 联系人
	 */
	private String contact;
	/**
	 * 联系人手机
	 */
	private String cellphone;
	/**
	 * 联系人性别
	 */
	private Integer contactSex;
	/**
	 * 消费开始时间
	 */
	private String startTime;
	/**
	 * 消费结束时间
	 */
	private String endTime;
	/**
	 * 下单时间
	 */
	private Date createOrderTime;
	/**
	 * 房型标题
	 */
	private String productTitle;
	
	/**
	 * 数量
	 */
	private CountModel counts;
	
	/**
	 * 出游人信息集合
	 */
	private List<PassengerModel> passengers;
	/**
	 * 房型信息
	 */
	private List<ResourceModel> resources;
	/**
	 * 最晚取消时间
	 */
	private String lasestCancelTime;
	/**
	 * 订单预订模式（默认值 0：待同程确认/审核后支付，1：直接支付，2：前台支付；3：担保）
	 */
	private Integer paymentFlag;
	
	
	
	public Integer getOwner() {
		return owner;
	}
	public void setOwner(Integer owner) {
		this.owner = owner;
	}
	public String getHotelOrderNo() {
		return hotelOrderNo;
	}
	public void setHotelOrderNo(String hotelOrderNo) {
		this.hotelOrderNo = hotelOrderNo;
	}
	public Long getSaleOrderNo() {
		return saleOrderNo;
	}
	public void setSaleOrderNo(Long saleOrderNo) {
		this.saleOrderNo = saleOrderNo;
	}
	public Long getBuyOrderNo() {
		return buyOrderNo;
	}
	public void setBuyOrderNo(Long buyOrderNo) {
		this.buyOrderNo = buyOrderNo;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public BigDecimal getTotalRefund() {
		return totalRefund;
	}
	public void setTotalRefund(BigDecimal totalRefund) {
		this.totalRefund = totalRefund;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public Long getBankId() {
		return bankId;
	}
	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getCardUserName() {
		return cardUserName;
	}
	public void setCardUserName(String cardUserName) {
		this.cardUserName = cardUserName;
	}
	public String getCardValueDate() {
		return cardValueDate;
	}
	public void setCardValueDate(String cardValueDate) {
		this.cardValueDate = cardValueDate;
	}
	public String getCredentialName() {
		return credentialName;
	}
	public void setCredentialName(String credentialName) {
		this.credentialName = credentialName;
	}
	public String getCredentialNumber() {
		return credentialNumber;
	}
	public void setCredentialNumber(String credentialNumber) {
		this.credentialNumber = credentialNumber;
	}
	public String getCardMobile() {
		return cardMobile;
	}
	public void setCardMobile(String cardMobile) {
		this.cardMobile = cardMobile;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public BigDecimal getOrderMoney() {
		return orderMoney;
	}
	public void setOrderMoney(BigDecimal orderMoney) {
		this.orderMoney = orderMoney;
	}
	public Boolean getIsAffirm() {
		return isAffirm;
	}
	public void setIsAffirm(Boolean isAffirm) {
		this.isAffirm = isAffirm;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getHotelAddress() {
		return hotelAddress;
	}
	public void setHotelAddress(String hotelAddress) {
		this.hotelAddress = hotelAddress;
	}
	public String getHotelPhone() {
		return hotelPhone;
	}
	public void setHotelPhone(String hotelPhone) {
		this.hotelPhone = hotelPhone;
	}
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
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Date getCreateOrderTime() {
		return createOrderTime;
	}
	public void setCreateOrderTime(Date createOrderTime) {
		this.createOrderTime = createOrderTime;
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
	public SaleOrder getSaleOrder() {
		return saleOrder;
	}
	public void setSaleOrder(SaleOrder saleOrder) {
		this.saleOrder = saleOrder;
	}
	public Integer getNightCount() {
		return nightCount;
	}
	public void setNightCount(Integer nightCount) {
		this.nightCount = nightCount;
	}
	public String getGuestName() {
		return guestName;
	}
	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}
	public String getGuestMobile() {
		return guestMobile;
	}
	public void setGuestMobile(String guestMobile) {
		this.guestMobile = guestMobile;
	}
	public String getArriveHotelTime() {
		return arriveHotelTime;
	}
	public void setArriveHotelTime(String arriveHotelTime) {
		this.arriveHotelTime = arriveHotelTime;
	}
	public String getEachNightPrice() {
		return eachNightPrice;
	}
	public void setEachNightPrice(String eachNightPrice) {
		this.eachNightPrice = eachNightPrice;
	}
	public String getDbOrderType() {
		return dbOrderType;
	}
	public void setDbOrderType(String dbOrderType) {
		this.dbOrderType = dbOrderType;
	}
	public BigDecimal getDbOrderMoney() {
		return dbOrderMoney;
	}
	public void setDbOrderMoney(BigDecimal dbOrderMoney) {
		this.dbOrderMoney = dbOrderMoney;
	}
	public String getDbCancelRule() {
		return dbCancelRule;
	}
	public void setDbCancelRule(String dbCancelRule) {
		this.dbCancelRule = dbCancelRule;
	}
	public Date getCancelPenalty() {
		return cancelPenalty;
	}
	public void setCancelPenalty(Date cancelPenalty) {
		this.cancelPenalty = cancelPenalty;
	}
	public Date getEarlyArriveTime() {
		return earlyArriveTime;
	}
	public void setEarlyArriveTime(Date earlyArriveTime) {
		this.earlyArriveTime = earlyArriveTime;
	}
	public Date getLatestArriveTime() {
		return latestArriveTime;
	}
	public void setLatestArriveTime(Date latestArriveTime) {
		this.latestArriveTime = latestArriveTime;
	}
	public String getRequestText() {
		return requestText;
	}
	public void setRequestText(String requestText) {
		this.requestText = requestText;
	}
	public String getRequestName() {
		return requestName;
	}
	public void setRequestName(String requestName) {
		this.requestName = requestName;
	}
}
