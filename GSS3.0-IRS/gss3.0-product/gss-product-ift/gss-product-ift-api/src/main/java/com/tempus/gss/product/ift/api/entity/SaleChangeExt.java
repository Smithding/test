package com.tempus.gss.product.ift.api.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.order.entity.DifferenceOrder;
import com.tempus.gss.order.entity.SaleChange;
import com.tempus.gss.serializer.LongSerializer;
import org.springframework.data.annotation.Transient;

public class SaleChangeExt implements Serializable {
	/**
	 * 销售单废退改编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long saleChangeNo;
	
	/**
	 * 销售变更单
	 */
	private SaleChange saleChange;

	/**
	 * ID
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long id;

	/**
	 * 数据归属单位
	 */
	private Integer owner;

	/**
	 * 废退方式：1：自愿、2：非自愿
	 */
	private Integer refundWay;

	/**
	 * 操作人 默认为：sys
	 */
	private String modifier;

	/**
	 * 启用状态 1：启用，2：停用
	 */
	private String status;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	/**
	 * 删除标志 0 无效 已删除 1 有效
	 */
	private Byte valid;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 创建人
	 */
	private String creator;

	/**
	 * 将销售单锁定的用户的Id 有大于0的值，表示已被用户锁定，是该用户的Id.
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long locker;

	/**
	 * 锁定时间
	 */
	private Date lockTime;

	/**
	 * 业务类型 1废 2退 3改(同SaleChange的 orderChangeType 属性)
	 */
	private Integer changeType;

	/**
	 * 联系人名称
	 */
	private String contactName;

	/**
	 * 联系人手机号
	 */
	private String contactMobile;

	/**
	 * 用户备注
	 */
	private String customerRemark;

	/**
	 * 拒绝原因
	 */
	private String refuseReason;
	
	/**
	 * 航司状态
	 */
	private Integer airlineStatus;
	
	/**
	 * 审核人员
	 */
	private String auditPerson;
	
	/**
	 * 审核时间
	 */
	private String auditTime;
	
	/**
	 * 退票文件上传路径
	 */
	private String refundFileUrl;

	/**
	 * 客商编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long customerNo;

	/**
	 * 客商类型编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long customerTypeNo;
	
	/**
	 * 改签类型
	 */
	private Integer ticketChangeType;
	
	/**
	 * 改签明细.
	 */
	private List<SaleChangeDetail> saleChangeDetailList;

	/**
	 * 关联乘客改签价格
	 */
	private List<PassengerChangePrice> passengerChangePriceList;

	/**
	 * 废退的乘客价格.
	 */
	private List<PassengerRefundPrice> passengerRefundPriceList;

	/**
	 * 关联销售订单明细
	 */
	private List<SaleOrderDetail> saleOrderDetailList;
	//ChildStatus 1 待审核 2 已审核 3 改签中 10 已改签、已退票、已废票 11已取消

	/**
	 * 改签备注
	 */
	private String changeRemark;
	/**
	 * PNR编号
	 */
	private Long pnrNo;
	/**
	 * 差异单明细
	 * @return
	 */
	private List<DifferenceOrder> differenceOrderList;
	//采购币种
	private  String currency;
	//销售币种
	private String saleCurrency;
	//汇率
	private BigDecimal exchangeRate;
	//操作人
	private String handlers;
	/**
	 * office
	 */
	private String office;

	//改签出票员   DRAWER_LOGIN_NAME
	private String drawerLoginName;

	//是否已退款
	private Integer isRefund;

	//提交航司后采购审核状态
	private Integer airLineRefundStatus;

	@Override
	public String toString() {
		return "SaleChangeExt{" +
				"saleChangeNo=" + saleChangeNo +
				", saleChange=" + saleChange +
				", id=" + id +
				", owner=" + owner +
				", refundWay=" + refundWay +
				", modifier='" + modifier + '\'' +
				", status='" + status + '\'' +
				", modifyTime=" + modifyTime +
				", valid=" + valid +
				", createTime=" + createTime +
				", creator='" + creator + '\'' +
				", locker=" + locker +
				", lockTime=" + lockTime +
				", changeType=" + changeType +
				", contactName='" + contactName + '\'' +
				", contactMobile='" + contactMobile + '\'' +
				", customerRemark='" + customerRemark + '\'' +
				", refuseReason='" + refuseReason + '\'' +
				", airlineStatus=" + airlineStatus +
				", auditPerson='" + auditPerson + '\'' +
				", auditTime='" + auditTime + '\'' +
				", refundFileUrl='" + refundFileUrl + '\'' +
				", customerNo=" + customerNo +
				", customerTypeNo=" + customerTypeNo +
				", ticketChangeType=" + ticketChangeType +
				", saleChangeDetailList=" + saleChangeDetailList +
				", passengerChangePriceList=" + passengerChangePriceList +
				", passengerRefundPriceList=" + passengerRefundPriceList +
				", saleOrderDetailList=" + saleOrderDetailList +
				", changeRemark='" + changeRemark + '\'' +
				", pnrNo=" + pnrNo +
				", differenceOrderList=" + differenceOrderList +
				", currency='" + currency + '\'' +
				", saleCurrency='" + saleCurrency + '\'' +
				", exchangeRate=" + exchangeRate +
				", handlers='" + handlers + '\'' +
				", office='" + office + '\'' +
				", drawerLoginName='" + drawerLoginName + '\'' +
				", isRefund=" + isRefund +
				", airLineRefundStatus=" + airLineRefundStatus +
				'}';
	}

	public String getDrawerLoginName() {
		return drawerLoginName;
	}

	public void setDrawerLoginName(String drawerLoginName) {
		this.drawerLoginName = drawerLoginName;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public String getHandlers() {
		return handlers;
	}

	public void setHandlers(String handlers) {
		this.handlers = handlers;
	}


	private static final long serialVersionUID = 1L;

	public Long getSaleChangeNo() {
		return saleChangeNo;
	}

	public void setSaleChangeNo(Long saleChangeNo) {
		this.saleChangeNo = saleChangeNo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getOwner() {
		return owner;
	}

	public void setOwner(Integer owner) {
		this.owner = owner;
	}

	public Integer getRefundWay() {
		return refundWay;
	}

	public void setRefundWay(Integer refundWay) {
		this.refundWay = refundWay;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Byte getValid() {
		return valid;
	}

	public void setValid(Byte valid) {
		this.valid = valid;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Long getLocker() {
		return locker;
	}

	public List<SaleChangeDetail> getSaleChangeDetailList() {

		return saleChangeDetailList;
	}

	public void setSaleChangeDetailList(List<SaleChangeDetail> saleChangeDetailList) {

		this.saleChangeDetailList = saleChangeDetailList;
	}

	public List<PassengerChangePrice> getPassengerChangePriceList() {

		return passengerChangePriceList;
	}

	public void setPassengerChangePriceList(
			List<PassengerChangePrice> passengerChangePriceList) {

		this.passengerChangePriceList = passengerChangePriceList;
	}

	public void setLocker(Long locker) {
		this.locker = locker;
	}

	public Date getLockTime() {
		return lockTime;
	}

	public void setLockTime(Date lockTime) {
		this.lockTime = lockTime;
	}

	public Integer getChangeType() {
		return changeType;
	}

	public void setChangeType(Integer changeType) {
		this.changeType = changeType;
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

	public String getCustomerRemark() {
		return customerRemark;
	}

	public void setCustomerRemark(String customerRemark) {
		this.customerRemark = customerRemark;
	}

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public SaleChange getSaleChange() {

		return saleChange;
	}

	public void setSaleChange(SaleChange saleChange) {

		this.saleChange = saleChange;
	}

	public List<PassengerRefundPrice> getPassengerRefundPriceList() {

		return passengerRefundPriceList;
	}

	public void setPassengerRefundPriceList(
			List<PassengerRefundPrice> passengerRefundPriceList) {

		this.passengerRefundPriceList = passengerRefundPriceList;
	}

	public Integer getAirlineStatus() {

		return airlineStatus;
	}

	public void setAirlineStatus(Integer airlineStatus) {

		this.airlineStatus = airlineStatus;
	}

	public String getAuditPerson() {
		return auditPerson;
	}

	public void setAuditPerson(String auditPerson) {
		this.auditPerson = auditPerson;
	}

	public String getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}

	public String getRefundFileUrl() {
		return refundFileUrl;
	}

	public void setRefundFileUrl(String refundFileUrl) {
		this.refundFileUrl = refundFileUrl;
	}

	public Long getCustomerNo() {

		return customerNo;
	}

	public void setCustomerNo(Long customerNo) {

		this.customerNo = customerNo;
	}

	public Long getCustomerTypeNo() {

		return customerTypeNo;
	}

	public void setCustomerTypeNo(Long customerTypeNo) {

		this.customerTypeNo = customerTypeNo;
	}

	public Integer getTicketChangeType() {
		return ticketChangeType;
	}

	public void setTicketChangeType(Integer ticketChangeType) {
		this.ticketChangeType = ticketChangeType;
	}

	public List<SaleOrderDetail> getSaleOrderDetailList() {
		return saleOrderDetailList;
	}

	public void setSaleOrderDetailList(List<SaleOrderDetail> saleOrderDetailList) {
		this.saleOrderDetailList = saleOrderDetailList;
	}

	public String getChangeRemark() {
		return changeRemark;
	}

	public void setChangeRemark(String changeRemark) {
		this.changeRemark = changeRemark;
	}

	public Long getPnrNo() {
		return pnrNo;
	}

	public void setPnrNo(Long pnrNo) {
		this.pnrNo = pnrNo;
	}

	public List<DifferenceOrder> getDifferenceOrderList() {
		return differenceOrderList;
	}

	public void setDifferenceOrderList(List<DifferenceOrder> differenceOrderList) {
		this.differenceOrderList = differenceOrderList;
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

	public String getSaleCurrency() {
		return saleCurrency;
	}

	public void setSaleCurrency(String saleCurrency) {
		this.saleCurrency = saleCurrency;
	}


	public Integer getAirLineRefundStatus() {
		return airLineRefundStatus;
	}

	public void setAirLineRefundStatus(Integer airLineRefundStatus) {
		this.airLineRefundStatus = airLineRefundStatus;
	}

	public Integer getIsRefund() {
		return isRefund;
	}

	public void setIsRefund(Integer isRefund) {
		this.isRefund = isRefund;
	}
}