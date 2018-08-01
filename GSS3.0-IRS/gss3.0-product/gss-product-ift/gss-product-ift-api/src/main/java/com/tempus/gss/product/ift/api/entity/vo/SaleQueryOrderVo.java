package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;

/**
 * Created by 杨威 on 2016/11/2.
 */
public class SaleQueryOrderVo implements Serializable {


	private static final long serialVersionUID = 3528529316948671647L;
	/**
	 * 产品类型
	 */
	private String productType;
	/**
	 * 预订日始
	 */
	private String bookStartDate;
	/**
	 * 预订日止
	 */
	private String bookEndDate;
	/**
	 * 支付日期
	 */
	private String payStartDate;
	/**
	 * 支付日期
	 */
	private String payEndDate;
	/**
	 * 起飞日期
	 */
	private String flyStartDate;
	/**
	 * 起飞结束日期
	 */
	private String flyStartEndDate;
	/**
	 * 到达日期
	 */
	private String flyEndDate;
	/**
	 * 到达结束日期
	 */
	private String flyEndArrDate;
	/**
	 * 客户类型
	 */
	private String customerType;
	/**
	 * 客户编号
	 */
	private String customerNo;
	/**
	 * PNR状态
	 */
	private String pnrState;
	/**
	 * PNR
	 */
	private String pnr;
	/**
	 * 订单状态集合 可多个状态同时查询
	 */
	private Integer[] orderStatusArray;
	/**
	 * 订单来源
	 */
	private String orderSource;
	/**
	 * 交易单号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long transationOrderNo;
	/**
	 * 票号
	 */
	private String ticketNo;
	/**
	 * 乘机人名称
	 */
	private String passenger;
	/**
	 * 航空公司
	 */
	private String airline;
	/**
	 * 出发城市
	 */
	private String departureCity;
	/**
	 * 到达城市
	 */
	private String arrivalCity;

	/**
	 * 联系人
	 */
	private String contacts;
	/**
	 * 联系电话
	 */
	private String phone;
	/**
	 * 操作员
	 */
	private String operator;
	/**
	 * 销售单号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long saleOrderNo;
	/**
	 * 乘机人证件号
	 */
	private String cardNo;
	/**
	 * 将销售单锁定的用户的Id 有大于0的值，表示已被用户锁定，是该用户的Id.
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long locker;
	@JsonSerialize(using = LongSerializer.class)
	private Long buyLocker;
	/**
	 * 订单状态
	 */
	private Integer orderStatus;
	/**
	 * 支付状态
	 */
	private Integer payStatus;
	/**
	 * 支付类型
	 */
	private Integer payType;
	private Integer owner;
	private byte valid;
	/**
	 * 供应商
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long supplierNo;
	
	/**
	 * 舱位
	 */
	private String cabin;
	
	/**
	 * 出票航司
	 */
	private String ticketAirline;
	
	/**
	 * 出票时间
	 */
	private Date issueTime;
	
	private String createType;
	
	private Integer[] createTypeStatusArray;

	/**可多个支付状态拼接1 为待支付 2 支付中 3 为已支付，4 为挂帐支付*/
	private String payStatuss;
	

	/** 关联类型: 1:主单; 2:补儿童; 3:补婴儿 */
	private Integer linkType = 1;

	/**是否查询下级分销商*/
	private Boolean customerCount=false;

	private List<Long> longList;

	/**处理人*/
	private String modifier;

	/**订单子状态*/
	private Integer orderChildStatus;
	
	/**订单子状态*/
	private Integer queryType;
	
	public Integer getQueryType() {
		return queryType;
	}
	
	public void setQueryType(Integer queryType) {
		this.queryType = queryType;
	}
	
	
	/**出票处理人*/
	private String issuemodifier;

	public List<Long> getLongList() {
		return longList;
	}

	public void setLongList(List<Long> longList) {
		this.longList = longList;
	}

	public Boolean getCustomerCount() {
		return customerCount;
	}

	public void setCustomerCount(Boolean customerCount) {
		this.customerCount = customerCount;
	}

	public Integer getLinkType() {
		return linkType;
	}

	public void setLinkType(Integer linkType) {
		this.linkType = linkType;
	}

	public String getProductType() {

		return productType;
	}

	public void setProductType(String productType) {

		this.productType = productType;
	}

	public String getBookStartDate() {

		return bookStartDate;
	}

	public void setBookStartDate(String bookStartDate) {

		this.bookStartDate = bookStartDate;
	}

	public String getBookEndDate() {

		return bookEndDate;
	}

	public void setBookEndDate(String bookEndDate) {

		this.bookEndDate = bookEndDate;
	}

	public String getPayStartDate() {

		return payStartDate;
	}

	public void setPayStartDate(String payStartDate) {

		this.payStartDate = payStartDate;
	}

	public String getPayEndDate() {

		return payEndDate;
	}

	public void setPayEndDate(String payEndDate) {

		this.payEndDate = payEndDate;
	}

	
	public String getFlyStartDate() {
	
		return flyStartDate;
	}

	
	public void setFlyStartDate(String flyStartDate) {
	
		this.flyStartDate = flyStartDate;
	}

	
	public String getFlyEndDate() {
	
		return flyEndDate;
	}

	
	public void setFlyEndDate(String flyEndDate) {
	
		this.flyEndDate = flyEndDate;
	}

	public String getCustomerType() {

		return customerType;
	}

	public void setCustomerType(String customerType) {

		this.customerType = customerType;
	}

	public String getCustomerNo() {

		return customerNo;
	}

	public void setCustomerNo(String customerNo) {

		this.customerNo = customerNo;
	}

	public String getPnrState() {

		return pnrState;
	}

	public void setPnrState(String pnrState) {

		this.pnrState = pnrState;
	}

	public String getPnr() {

		return pnr;
	}

	public void setPnr(String pnr) {

		this.pnr = pnr;
	}

	public Integer[] getOrderStatusArray() {

		return orderStatusArray;
	}

	public void setOrderStatusArray(Integer[] orderStatusArray) {

		this.orderStatusArray = orderStatusArray;
	}

	public String getOrderSource() {

		return orderSource;
	}

	public void setOrderSource(String orderSource) {

		this.orderSource = orderSource;
	}

	public Long getTransationOrderNo() {

		return transationOrderNo;
	}

	public void setTransationOrderNo(Long transationOrderNo) {

		this.transationOrderNo = transationOrderNo;
	}

	public String getTicketNo() {

		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {

		this.ticketNo = ticketNo;
	}

	public String getPassenger() {

		return passenger;
	}

	public void setPassenger(String passenger) {

		this.passenger = passenger;
	}

	public String getAirline() {

		return airline;
	}

	public void setAirline(String airline) {

		this.airline = airline;
	}

	public String getDepartureCity() {

		return departureCity;
	}

	public void setDepartureCity(String departureCity) {

		this.departureCity = departureCity;
	}

	public String getArrivalCity() {

		return arrivalCity;
	}

	public void setArrivalCity(String arrivalCity) {

		this.arrivalCity = arrivalCity;
	}

	public String getFlyStartEndDate() {
	
		return flyStartEndDate;
	}
	
	public void setFlyStartEndDate(String flyStartEndDate) {
	
		this.flyStartEndDate = flyStartEndDate;
	}

	
	public String getFlyEndArrDate() {
	
		return flyEndArrDate;
	}

	public void setFlyEndArrDate(String flyEndArrDate) {
	
		this.flyEndArrDate = flyEndArrDate;
	}

	public String getContacts() {

		return contacts;
	}

	public void setContacts(String contacts) {

		this.contacts = contacts;
	}

	public String getPhone() {

		return phone;
	}

	public void setPhone(String phone) {

		this.phone = phone;
	}

	public String getOperator() {

		return operator;
	}

	public void setOperator(String operator) {

		this.operator = operator;
	}

	public Long getSaleOrderNo() {

		return saleOrderNo;
	}

	public void setSaleOrderNo(Long saleOrderNo) {

		this.saleOrderNo = saleOrderNo;
	}

	public String getCardNo() {

		return cardNo;
	}

	public void setCardNo(String cardNo) {

		this.cardNo = cardNo;
	}
	
	public Long getLocker() {
	
		return locker;
	}
	
	public void setLocker(Long locker) {
	
		this.locker = locker;
	}
	
	public Integer getOrderStatus() {
	
		return orderStatus;
	}
	
	public void setOrderStatus(Integer orderStatus) {
	
		this.orderStatus = orderStatus;
	}

	public Integer getOwner() {
	
		return owner;
	}

	public void setOwner(Integer owner) {
	
		this.owner = owner;
	}

	public byte getValid() {
	
		return valid;
	}

	public void setValid(byte valid) {
	
		this.valid = valid;
	}


	public Long getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(Long supplierNo) {
		this.supplierNo = supplierNo;
	}

	public Integer getPayStatus() {
	
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
	
		this.payStatus = payStatus;
	}

	public Integer getPayType() {
	
		return payType;
	}
	
	public void setPayType(Integer payType) {
	
		this.payType = payType;
	}

	public String getCabin() {
		return cabin;
	}

	public void setCabin(String cabin) {
		this.cabin = cabin;
	}

	public String getTicketAirline() {
		return ticketAirline;
	}

	public void setTicketAirline(String ticketAirline) {
		this.ticketAirline = ticketAirline;
	}

	public Date getIssueTime() {
		return issueTime;
	}

	public void setIssueTime(Date issueTime) {
		this.issueTime = issueTime;
	}

	public String getCreateType() {
		return createType;
	}

	public void setCreateType(String createType) {
		this.createType = createType;
	}

	public Integer[] getCreateTypeStatusArray() {
		return createTypeStatusArray;
	}

	public void setCreateTypeStatusArray(Integer[] createTypeStatusArray) {
		this.createTypeStatusArray = createTypeStatusArray;
	}

	public String getPayStatuss() {
		return payStatuss;
	}

	public void setPayStatuss(String payStatuss) {
		this.payStatuss = payStatuss;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Integer getOrderChildStatus() {
		return orderChildStatus;
	}

	public void setOrderChildStatus(Integer orderChildStatus) {
		this.orderChildStatus = orderChildStatus;
	}

	public String getIssuemodifier() {
		return issuemodifier;
	}

	public void setIssuemodifier(String issuemodifier) {
		this.issuemodifier = issuemodifier;
	}

	public Long getBuyLocker() {
		return buyLocker;
	}

	public void setBuyLocker(Long buyLocker) {
		this.buyLocker = buyLocker;
	}
}
