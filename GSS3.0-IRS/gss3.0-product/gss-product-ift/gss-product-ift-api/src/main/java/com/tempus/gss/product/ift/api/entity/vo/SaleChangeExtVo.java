package com.tempus.gss.product.ift.api.entity.vo;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.product.ift.api.entity.Leg;
import com.tempus.gss.serializer.LongSerializer;

/**
 * Created by Administrator on 2016/10/20.
 */
/**
 * @author Administrator
 *
 */
public class SaleChangeExtVo implements Serializable{


	private static final long serialVersionUID = 6734725124414823563L;
	/**
	 * 申请开始日期
	 */

	private String beforeDate;

	/**
	 * 申请结束时间
	 */
	private String endDate;
	/**
	 * 废退该签单编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long saleChangeNo;

	/**
	 * 出票office 0:表示查询全部
	 */
	private String office;

	/**
	 * pnr
	 */
	private String pnr;

	/**
	 * 销售单编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long saleOrderNo;

	/**
	 * 订单明细编号
	 */
	private String saleOrderNoArray;

	/**
	 * 审核开始时间
	 */
	private String auditStartTime;

	/**
	 * 审核结束时间
	 */
	private String auditEndTime;
	/**
	 * 申请时间
	 */
	private String applyTimeArray;
	/**
	 * 订单来源
	 */
	private String source;
	/**
	 * 票价
	 */
	private String saleFare;
	/**
	 * 税费
	 */
	private String saleTax;
	/**
	 * 审核时间
	 */
	private String pnrArray;
	/**
	 * 支付状态
	 */
	private Integer[] payStatus;
	/**
	 * 起点机场-出发地
	 */
	private String depAirport;

	/**
	 * 终点机场-目的地
	 */
	private String arrAirport;
	/**
	 * 起飞开始时间
	 */
	private String depStartTime;

	/**
	 * 起飞结束时间
	 */
	private String depEndTime;
	/**
	 * 航班号/舱位
	 */
	private String flightSpaceArray;
	/**
	 * 乘客名字
	 */
	private String pgerName;

	/**
	 * 出票类型
	 */
	private String ticketType;

	/**
	 *业务类型 0（1废 2退） 1废 2退 3改
	 */
	private Integer changeType;

	private String saleCurrency;


	/**(0表示非锁定状态)
	 * 状态 1:待处理(订单子状态为：1,locker:0)
	 * 23已处理 (订单子状态为：10,11 locker:0)
	 * 22处理中(订单子状态为：1,2 当子状态为1时locker为1  当子状态为2时locker为0 )
	 * 24退票订单中的退款中有锁单操作(订单子状态为：1，2  当子状态为1时Locker为1  当子状态为2时Locker为1)只适用退票订单的退款中列表查询（因为该列表的子状态全部是2一开始没有点退票时locker为0，点击了退票又返回此时子状态为2locker为1所以22状态是查询不出来）
	 */
	private String status;

	/**(0表示非锁定状态)
	 * 状态 1:待处理(订单子状态为：1,locker:0)
	 * 2已处理
	 */
	private String[] buyStatus;

	/**
	 * owner
	 */
	private Integer owner;
	/**
	 * valid
	 */
	private Byte valid;
	/**
	 * 查看,审核标志
	 * read=查看
	 * audit=审核
	 */
	private String flag;

	/**
	 * 操作人
	 */
	private String creator;
	/**
	 * 废退方式：1：自愿、2：非自愿  0:全部(包括1 2 )
	 */
	private Integer refundWay;

	/**
	 * locker
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long locker;
	@JsonSerialize(using = LongSerializer.class)
	private Long buyLocker;
	/**
	 * 供应商
	 */
	private String  supplierShortName;

	/** 交易单编号 */
	@JsonSerialize(using = LongSerializer.class)
	private Long transationOrderNo;

	//乘客类型1：ADT:2：CHD，3：INF
	//成人类型
	private List<PassengerVo> adtCountList;
	private List<PassengerVo> chdCountList;

	private List<PassengerVo> infCountList;

	//审核人
	private String modifier;


	//操作人
	private  String handlers;



	/**
	 * 原航班信息
	 */
	private List<LegVo> legList;
	/**
	 * 原航班信息
	 */
	private List<Leg> oldLegList;
	/**
	 * 新航班信息
	 */
	private List<Leg> newLegList;
	/**
	 * 乘客信息
	 */
	private Set<PassengerVo> pgerList;
	/**
	 * 子状态  1.未审核、2.已审核、6、改签中、10 已完成、 11已取消   集合可多个状态进行查询
	 * @return
	 */
	private Integer[] childStatus;

	/**
	 * 票号
	 */
	private String ticketNo;

	/**
	 * 日期类型 1：申请日期  2：审核日期
	 */
	private Integer dateType;
	/**
	 *
	 *航程
	 */
	private String legArray;

	@JsonSerialize(using = LongSerializer.class)
	private Long supplierNo;

	/**
	 * 审核时间（前台展示）
	 */
	private String auditTimeArray;

	private String isNotRefund;

	private BigDecimal amount;

	/**
	 * 改签类型 改签类型 1升舱 2改期 3换开
	 */
	private Integer ticketChangeType;

	private String contactName;

	private String contactMobile;

	private Integer toDetailStatus;

	private String airline;
	@JsonSerialize(using = LongSerializer.class)
	private Long AgentId;
	@JsonSerialize(using = LongSerializer.class)
	private Long customerTypeNo;
	@JsonSerialize(using = LongSerializer.class)
	private Long customerNo;
	/**
	 * 用户备注
	 */
	private String customerRemark;

	private int curPage;// 当前页

	private int rowNum;// 每页多少行

	/*是否查询下级分销商*/
	private Boolean customerCount=false;

	private List<Long> longList;

	private String currency;

	private BigDecimal exchangeRate;

	/**
	 * 0采购 1销售
	 */
	private Integer toDoType;

	private String customerName;

	//提交航司后采购审核状态
	private Integer[] airLineRefundStatus;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Boolean getCustomerCount() {
		return customerCount;
	}

	public void setCustomerCount(Boolean customerCount) {
		this.customerCount = customerCount;
	}

	public List<Long> getLongList() {
		return longList;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setLongList(List<Long> longList) {
		this.longList = longList;
	}

	public String getLegArray() {
		return legArray;
	}

	public void setLegArray(String legArray) {
		this.legArray = legArray;
	}

	public String getSaleCurrency() {
		return saleCurrency;
	}

	public void setSaleCurrency(String saleCurrency) {
		this.saleCurrency = saleCurrency;
	}

	public String getBeforeDate() {

		return beforeDate;
	}

	public void setBeforeDate(String beforeDate) {

		this.beforeDate = beforeDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {

		this.endDate = endDate;
	}

	public String getOffice() {

		return office;
	}

	public void setOffice(String office) {

		this.office = office;
	}

	public String getAuditStartTime() {

		return auditStartTime;
	}

	public void setAuditStartTime(String auditStartTime) {

		this.auditStartTime = auditStartTime;
	}

	public String getAuditEndTime() {

		return auditEndTime;
	}

	public void setAuditEndTime(String auditEndTime) {

		this.auditEndTime = auditEndTime;
	}

	public String getPnrArray() {

		return pnrArray;
	}

	public void setPnrArray(String pnrArray) {

		this.pnrArray = pnrArray;
	}

	public String getDepStartTime() {

		return depStartTime;
	}

	public void setDepStartTime(String depStartTime) {

		this.depStartTime = depStartTime;
	}

	public String getDepEndTime() {

		return depEndTime;
	}

	public void setDepEndTime(String depEndTime) {

		this.depEndTime = depEndTime;
	}

	public String getFlightSpaceArray() {

		return flightSpaceArray;
	}

	public Long getCustomerTypeNo() {

		return customerTypeNo;
	}

	public void setCustomerTypeNo(Long customerTypeNo) {

		this.customerTypeNo = customerTypeNo;
	}

	public Long getCustomerNo() {

		return customerNo;
	}

	public void setCustomerNo(Long customerNo) {

		this.customerNo = customerNo;
	}

	public void setFlightSpaceArray(String flightSpaceArray) {

		this.flightSpaceArray = flightSpaceArray;
	}

	public String getPgerName() {

		return pgerName;
	}

	public void setPgerName(String pgerName) {

		this.pgerName = pgerName;
	}

	public String getTicketType() {
		return ticketType;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}


	public Integer getOwner() {

		return owner;
	}

	public void setOwner(Integer owner) {

		this.owner = owner;
	}

	public Byte getValid() {

		return valid;
	}

	public void setValid(Byte valid) {

		this.valid = valid;
	}

	public Integer getChangeType() {

		return changeType;
	}

	public void setChangeType(Integer changeType) {

		this.changeType = changeType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getLocker() {
		return locker;
	}

	public void setLocker(Long locker) {
		this.locker = locker;
	}

	public String getDepAirport() {

		return depAirport;
	}

	public void setDepAirport(String depAirport) {

		this.depAirport = depAirport;
	}

	public String getArrAirport() {

		return arrAirport;
	}

	public void setArrAirport(String arrAirport) {

		this.arrAirport = arrAirport;
	}

	public String getApplyTimeArray() {

		return applyTimeArray;
	}

	public void setApplyTimeArray(String applyTimeArray) {

		this.applyTimeArray = applyTimeArray;
	}

	public String getSource() {

		return source;
	}

	public void setSource(String source) {

		this.source = source;
	}


	public String getSaleFare() {

		return saleFare;
	}


	public void setSaleFare(String saleFare) {

		this.saleFare = saleFare;
	}


	public String getSaleTax() {

		return saleTax;
	}

	public void setSaleTax(String saleTax) {

		this.saleTax = saleTax;
	}

	public Long getSaleOrderNo() {

		return saleOrderNo;
	}

	public void setSaleOrderNo(Long saleOrderNo) {

		this.saleOrderNo = saleOrderNo;
	}

	public String getModifier() {

		return modifier;
	}

	public void setModifier(String modifier) {

		this.modifier = modifier;
	}



	public List<Leg> getOldLegList() {
		return oldLegList;
	}

	public void setOldLegList(List<Leg> oldLegList) {
		this.oldLegList = oldLegList;
	}

	public List<Leg> getNewLegList() {
		return newLegList;
	}

	public void setNewLegList(List<Leg> newLegList) {
		this.newLegList = newLegList;
	}

	public Set<PassengerVo> getPgerList() {

		return pgerList;
	}

	public void setPgerList(Set<PassengerVo> pgerList) {

		this.pgerList = pgerList;
	}



	public Integer[] getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer[] payStatus) {
		this.payStatus = payStatus;
	}

	public List<LegVo> getLegList() {

		return legList;
	}

	public void setLegList(List<LegVo> legList) {

		this.legList = legList;
	}

	public String getSaleOrderNoArray() {

		return saleOrderNoArray;
	}

	public void setSaleOrderNoArray(String saleOrderNoArray) {

		this.saleOrderNoArray = saleOrderNoArray;
	}


	public List<PassengerVo> getAdtCountList() {

		return adtCountList;
	}


	public void setAdtCountList(List<PassengerVo> adtCountList) {

		this.adtCountList = adtCountList;
	}

	public List<PassengerVo> getChdCountList() {

		return chdCountList;
	}

	public void setChdCountList(List<PassengerVo> chdCountList) {

		this.chdCountList = chdCountList;
	}

	public List<PassengerVo> getInfCountList() {

		return infCountList;
	}

	public void setInfCountList(List<PassengerVo> infCountList) {

		this.infCountList = infCountList;
	}

	public String getPnr() {

		return pnr;
	}

	public void setPnr(String pnr) {

		this.pnr = pnr;
	}


	public Long getSaleChangeNo() {

		return saleChangeNo;
	}


	public void setSaleChangeNo(Long saleChangeNo) {

		this.saleChangeNo = saleChangeNo;
	}


	public String getFlag() {

		return flag;
	}


	public void setFlag(String flag) {

		this.flag = flag;
	}

	public String getTicketNo() {

		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {

		this.ticketNo = ticketNo;
	}

	public Integer getRefundWay() {
		return refundWay;
	}

	public void setRefundWay(Integer refundWay) {
		this.refundWay = refundWay;
	}

	public Integer getDateType() {
		return dateType;
	}

	public void setDateType(Integer dateType) {
		this.dateType = dateType;
	}

	public Integer[] getChildStatus() {
		return childStatus;
	}

	public void setChildStatus(Integer[] childStatus) {
		this.childStatus = childStatus;
	}

	public String getCreator() {

		return creator;
	}

	public void setCreator(String creator) {

		this.creator = creator;
	}

	public String getAuditTimeArray() {

		return auditTimeArray;
	}

	public void setAuditTimeArray(String auditTimeArray) {

		this.auditTimeArray = auditTimeArray;
	}

	public Long getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(Long supplierNo) {
		this.supplierNo = supplierNo;
	}

	public Integer getTicketChangeType() {
		return ticketChangeType;
	}

	public void setTicketChangeType(Integer ticketChangeType) {
		this.ticketChangeType = ticketChangeType;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public Integer getToDetailStatus() {
		return toDetailStatus;
	}

	public void setToDetailStatus(Integer toDetailStatus) {
		this.toDetailStatus = toDetailStatus;
	}

	public Long getAgentId() {
		return AgentId;
	}

	public void setAgentId(Long agentId) {
		AgentId = agentId;
	}

	public String getSupplierShortName() {
		return supplierShortName;
	}

	public void setSupplierShortName(String supplierShortName) {
		this.supplierShortName = supplierShortName;
	}

	public Long getTransationOrderNo() {
		return transationOrderNo;
	}

	public void setTransationOrderNo(Long transationOrderNo) {
		this.transationOrderNo = transationOrderNo;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public String getCustomerRemark() {
		return customerRemark;
	}

	public void setCustomerRemark(String customerRemark) {
		this.customerRemark = customerRemark;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public Integer getToDoType() {
		return toDoType;
	}

	public void setToDoType(Integer toDoType) {
		this.toDoType = toDoType;
	}

	public String[] getBuyStatus() {
		return buyStatus;
	}

	public void setBuyStatus(String[] buyStatus) {
		this.buyStatus = buyStatus;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String getHandlers() {
		return handlers;
	}

	public void setHandlers(String handlers) {
		this.handlers = handlers;
	}

	public Integer[] getAirLineRefundStatus() {
		return airLineRefundStatus;
	}

	public void setAirLineRefundStatus(Integer[] airLineRefundStatus) {
		this.airLineRefundStatus = airLineRefundStatus;
	}

	public String getIsNotRefund() {
		return isNotRefund;
	}

	public void setIsNotRefund(String isNotRefund) {
		this.isNotRefund = isNotRefund;
	}

	public Long getBuyLocker() {
		return buyLocker;
	}

	public void setBuyLocker(Long buyLocker) {
		this.buyLocker = buyLocker;
	}
}
