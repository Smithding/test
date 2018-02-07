package com.tempus.gss.product.ift.api.entity.iftVo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.order.entity.SaleChange;
import com.tempus.gss.serializer.LongSerializer;

import java.io.Serializable;
import java.util.List;

public class IftSaleChangeExt implements Serializable {
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
	 * 废退方式：1：自愿、2：非自愿
	 */
	private Integer refundWay;

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
	 * 
	 */
	private Integer ticketChangeType;
	
	/**
	 * 改签明细.
	 */
	private List<IftSaleChangeDetail> saleChangeDetailList;

	/**
	 * 关联乘客改签价格
	 */
	private List<IftPassengerChangePrice> passengerChangePriceList;

	/**
	 * 废退的乘客价格.
	 */
	private List<IftPassengerRefundPrice> passengerRefundPriceList;

	//ChildStatus 1 待审核 2 已审核 3 改签中 10 已改签、已退票、已废票 11已取消

	private static final long serialVersionUID = 1L;

	public Long getSaleChangeNo() {
		return saleChangeNo;
	}

	public void setSaleChangeNo(Long saleChangeNo) {
		this.saleChangeNo = saleChangeNo;
	}

	public Integer getRefundWay() {
		return refundWay;
	}

	public void setRefundWay(Integer refundWay) {
		this.refundWay = refundWay;
	}


	public List<IftSaleChangeDetail> getSaleChangeDetailList() {

		return saleChangeDetailList;
	}

	public void setSaleChangeDetailList(List<IftSaleChangeDetail> saleChangeDetailList) {

		this.saleChangeDetailList = saleChangeDetailList;
	}

	public List<IftPassengerChangePrice> getPassengerChangePriceList() {

		return passengerChangePriceList;
	}

	public void setPassengerChangePriceList(
			List<IftPassengerChangePrice> passengerChangePriceList) {

		this.passengerChangePriceList = passengerChangePriceList;
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

	public List<IftPassengerRefundPrice> getPassengerRefundPriceList() {

		return passengerRefundPriceList;
	}

	public void setPassengerRefundPriceList(
			List<IftPassengerRefundPrice> passengerRefundPriceList) {

		this.passengerRefundPriceList = passengerRefundPriceList;
	}

	public Integer getAirlineStatus() {

		return airlineStatus;
	}

	public void setAirlineStatus(Integer airlineStatus) {

		this.airlineStatus = airlineStatus;
	}

	public Integer getTicketChangeType() {
		return ticketChangeType;
	}

	public void setTicketChangeType(Integer ticketChangeType) {
		this.ticketChangeType = ticketChangeType;
	}
	
}