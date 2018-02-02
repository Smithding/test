package com.tempus.gss.product.hol.api.entity.request.tc;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.tempus.gss.order.entity.BuyOrder;
import com.tempus.gss.order.entity.SaleOrder;

/**
 * 酒店创建订单接口入参
 * @author kai.yang
 *
 */
public class OrderCreateReq implements Serializable{
	private static final long serialVersionUID = 1L; 
	/**
     * 供应GSS编号
     */
	@JSONField(serialize = false)
    private String supplierSource;
	
    /** 供应商编号 101:千淘 102:艺龙  103同程*/
	 @JSONField(serialize = false)
	private String supplierCode;
    
	/**
     * 采购单实体
     */
    @JSONField(serialize = false)
    private BuyOrder buyOrder;
    
    /**
     * 销售单实体
     */
    @JSONField(serialize = false)
    private SaleOrder saleOrder;
    /**
     * 服务费(供TMC使用)
     */
    @JSONField(serialize = false)
    private BigDecimal serviceFee;
    /**
	 * 酒店名称
	 */
    @JSONField(serialize = false)
	private String resName;
	/**
     * 订单类型 1:内 2:国际
     */
    @JSONField(serialize = false)
    private Integer orderType;
	/**
	 * 酒店 Id
	 */
	@JSONField(name = "ResId")
	private Long resId;
	/**
	 * 房型 Id
	 */
	@JSONField(name = "ProId")
	private String proId;
	/**
	 * 成人数（成人数，与儿童数必传一个）
	 */
	@JSONField(name = "OrderAdults")
	private Integer orderAdults;
	/**
	 * 儿童数（成人数，与儿童数必传一个）
	 */
	@JSONField(name = "OrderChilds")
	private Integer orderChilds;
	/**
	 * 预定份数
	 */
	@JSONField(name = "BookCount")
	private Integer bookCount;
	/**
	 * 酒店使用明细
	 */
	@JSONField(name = "OrderUseDateDetails")
	private List<ResourceUseDateDetail> orderUseDateDetails;
	/**
	 * 出游人信息集合
	 */
	@JSONField(name = "OrderPassengerDetails")
	private List<TravlePassengerInfo> orderPassengerDetails;
	/**
	 * 特殊要求
	 */
	@JSONField(name = "OrderRemark")
	private String orderRemark;
	/**
	 * 销售策略 Id
	 */
	@JSONField(name = "ProductUniqueId")
	private Integer productUniqueId;
	/**
	 * 联系人姓名
	 */
	@JSONField(name = "LinkManName")
	private String linkManName;
	/**
	 * 联系人性别（0 女，1 男）
	 */
	@JSONField(name = "LinkManSex")
	private Integer linkManSex;
	/**
	 * 联系人手机号
	 */
	@JSONField(name = "LinkManMobile")
	private String linkManMobile;
	/**
	 * 外部订单号
	 */
	@JSONField(name = "OutSideOrderId")
	private String outSideOrderId;
	/**
	 * 外部扩展信息
	 */
	@JSONField(name = "OutSideOrderInfo")
	private String outSideOrderInfo;
	/**
	 * 是否立即支付
	 */
	@JSONField(name = "IsPay")
	private Boolean isPay;
	/**
	 * 最晚到店时间 只能是整点，默认 18 点，超过 24表示次日，如 25 为次日 1点
	 */
	@JSONField(name = "ArrivalTime")
	private Integer arrivalTime;
	/**
	 * 支付模式 1.预付 2.现付默认预付
	 */
	@JSONField(name = "PaymentSign")
	private Integer paymentSign;
	/**
	 * 银行 Id（现付）
	 */
	@JSONField(name = "BankId")
	private Integer bankId;
	/**
	 * 银行名称（现付）
	 */
	@JSONField(name = "BankName")
	private String bankName;
	/**
	 * 卡号（现付）
	 */
	@JSONField(name = "CardNumber")
	private String cardNumber;
	/**
	 * 持卡人姓名（现付）
	 */
	@JSONField(name = "CardUserName")
	private String cardUserName;
	/**
	 * 验证码（现付）
	 */
	@JSONField(name = "CardValueCode")
	private String cardValueCode;
	/**
	 * 信用卡有效期，传入格式yyyy/MM 如：2014/02（现付）
	 */
	@JSONField(name = "CardValueDate")
	private String cardValueDate;
	/**
	 * 证件名称（现付）
	 */
	@JSONField(name = "CredentialName")
	private String credentialName;
	/**
	 * 证件号（现付）
	 */
	@JSONField(name = "CredentialNumber")
	private String credentialNumber;
	/**
	 * 手机号（现付）
	 */
	@JSONField(name = "Mobile")
	private String mobile;
	/**
	 * 客人特殊要求
	 */
	@JSONField(name = "SpecialRequestOption")
	private List<SpecialRequest> specialRequestOption;
	/**
	 * 酒店住址
	 */
	@JSONField(serialize = false)
	private String hotelAddress;
	/**
	 * 酒店电话
	 */
	@JSONField(serialize = false)
	private String hotelPhone;
	/**
	 * 房型名称
	 */
	@JSONField(serialize = false)
	private String proName;
	/**
	 * yyy-MM-dd HH:mm:ss 格式的到店时间
	 */
	@JSONField(serialize = false)
	private String arriveHotelTime;
	/**
	 *担保类型
	 */
	@JSONField(serialize = false)
	private String dbOrderType;
	/**
	 * 担保金额
	 */
	@JSONField(serialize = false)
	private BigDecimal dbOrderMoney;
	/**
	 * 取消规则
	 */
	@JSONField(serialize = false)
	private String dbCancelRule;
	/**
	 * 最迟取消时间这个时间前取消不需要扣罚金
	 */
	@JSONField(serialize = false)
	private Date cancelPenalty;
	/**
	 * 最早到店限制时间
	 */
	@JSONField(serialize = false)
	private Date earlyArriveTime;
	/**
	 * 最晚到店时间限制
	 */
	@JSONField(serialize = false)
	private Date latestArriveTime;
	/**
	 * 供应商编号
	 */
	@JSONField(serialize = false)
	private String supplierNo;
	/**
	 * 前台传入计算好的总价
	 */
	@JSONField(serialize = false)
	private BigDecimal beforeTotalPrice;
	/**
	 * 早餐分数
	 */
	@JSONField(serialize = false)
	private String breakfastCount;
	/**
	 * 前台传入的总佣金
	 */
	@JSONField(serialize = false)
	private BigDecimal totalRebateRateProfit;
	
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
	public Integer getOrderAdults() {
		return orderAdults;
	}
	public void setOrderAdults(Integer orderAdults) {
		this.orderAdults = orderAdults;
	}
	public Integer getOrderChilds() {
		return orderChilds;
	}
	public void setOrderChilds(Integer orderChilds) {
		this.orderChilds = orderChilds;
	}
	public Integer getBookCount() {
		return bookCount;
	}
	public void setBookCount(Integer bookCount) {
		this.bookCount = bookCount;
	}
	//@JsonGetter("orderUseDateDetails")
	public List<ResourceUseDateDetail> getOrderUseDateDetails() {
		return orderUseDateDetails;
	}
	/*@JsonIgnore
	public String getOrderUseDateDetailsObj(){
		return JsonUtil.toJson(orderUseDateDetails);
	}*/
	
	public void setOrderUseDateDetails(List<ResourceUseDateDetail> orderUseDateDetails) {
		this.orderUseDateDetails = orderUseDateDetails;
	}
	//@JsonGetter("orderPassengerDetails")
	public List<TravlePassengerInfo> getOrderPassengerDetails() {
		return orderPassengerDetails;
	}
	/*@JsonIgnore
	public String getOrderPassengerDetailsObj(){
		return JsonUtil.toJson(orderPassengerDetails);
	}*/
	
	public void setOrderPassengerDetails(List<TravlePassengerInfo> orderPassengerDetails) {
		this.orderPassengerDetails = orderPassengerDetails;
	}
	public String getOrderRemark() {
		return orderRemark;
	}
	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}
	public Integer getProductUniqueId() {
		return productUniqueId;
	}
	public void setProductUniqueId(Integer productUniqueId) {
		this.productUniqueId = productUniqueId;
	}
	public String getLinkManName() {
		return linkManName;
	}
	public void setLinkManName(String linkManName) {
		this.linkManName = linkManName;
	}
	public Integer getLinkManSex() {
		return linkManSex;
	}
	public void setLinkManSex(Integer linkManSex) {
		this.linkManSex = linkManSex;
	}
	public String getLinkManMobile() {
		return linkManMobile;
	}
	public void setLinkManMobile(String linkManMobile) {
		this.linkManMobile = linkManMobile;
	}
	public String getOutSideOrderId() {
		return outSideOrderId;
	}
	public void setOutSideOrderId(String outSideOrderId) {
		this.outSideOrderId = outSideOrderId;
	}
	public String getOutSideOrderInfo() {
		return outSideOrderInfo;
	}
	public void setOutSideOrderInfo(String outSideOrderInfo) {
		this.outSideOrderInfo = outSideOrderInfo;
	}
	public Boolean getIsPay() {
		return isPay;
	}
	public void setIsPay(Boolean isPay) {
		this.isPay = isPay;
	}
	public Integer getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(Integer arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public Integer getPaymentSign() {
		return paymentSign;
	}
	public void setPaymentSign(Integer paymentSign) {
		this.paymentSign = paymentSign;
	}
	public Integer getBankId() {
		return bankId;
	}
	public void setBankId(Integer bankId) {
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
	public String getCardValueCode() {
		return cardValueCode;
	}
	public void setCardValueCode(String cardValueCode) {
		this.cardValueCode = cardValueCode;
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
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public List<SpecialRequest> getSpecialRequestOption() {
		return specialRequestOption;
	}
	public void setSpecialRequestOption(List<SpecialRequest> specialRequestOption) {
		this.specialRequestOption = specialRequestOption;
	}
	public BuyOrder getBuyOrder() {
		return buyOrder;
	}
	public void setBuyOrder(BuyOrder buyOrder) {
		this.buyOrder = buyOrder;
	}
	public SaleOrder getSaleOrder() {
		return saleOrder;
	}
	public void setSaleOrder(SaleOrder saleOrder) {
		this.saleOrder = saleOrder;
	}
	public BigDecimal getServiceFee() {
		return serviceFee;
	}
	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
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
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getArriveHotelTime() {
		return arriveHotelTime;
	}
	public void setArriveHotelTime(String arriveHotelTime) {
		this.arriveHotelTime = arriveHotelTime;
	}
	public String getSupplierSource() {
		return supplierSource;
	}
	public void setSupplierSource(String supplierSource) {
		this.supplierSource = supplierSource;
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
	public String getSupplierNo() {
		return supplierNo;
	}
	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}
	public BigDecimal getBeforeTotalPrice() {
		return beforeTotalPrice;
	}
	public void setBeforeTotalPrice(BigDecimal beforeTotalPrice) {
		this.beforeTotalPrice = beforeTotalPrice;
	}
	public String getBreakfastCount() {
		return breakfastCount;
	}
	public void setBreakfastCount(String breakfastCount) {
		this.breakfastCount = breakfastCount;
	}
	public BigDecimal getTotalRebateRateProfit() {
		return totalRebateRateProfit;
	}
	public void setTotalRebateRateProfit(BigDecimal totalRebateRateProfit) {
		this.totalRebateRateProfit = totalRebateRateProfit;
	}
	
}
