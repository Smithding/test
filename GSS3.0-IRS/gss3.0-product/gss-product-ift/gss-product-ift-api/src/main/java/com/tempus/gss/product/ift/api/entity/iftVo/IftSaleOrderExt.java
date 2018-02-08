package com.tempus.gss.product.ift.api.entity.iftVo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.order.entity.SaleOrder;
import com.tempus.gss.serializer.LongSerializer;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 销售订单扩展.
 */
public class IftSaleOrderExt implements Serializable {

	@JsonSerialize(using = LongSerializer.class)
	private static final long serialVersionUID = 1L;
	/**
	 * 订单编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long saleOrderNo;
	/**
	 * 销售单.
	 */
	private SaleOrder saleOrder;
	
	/**
	 * 需求单编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long demandNo;

	/**
	 * 需求单
	 */
	private IftDemand demand;

	/**
	 * 航程类型 ：1:单程; 2:往返.
	 */
	private Integer legType;
	/**
	 * 联系人名称
	 */
	private String contactName;
	/**
	 * 联系人电话
	 */
	private String contactPhone;
	/**
	 * 联系人手机号
	 */
	private String contactMobile;
	/**
	 * 联系人邮箱
	 */
	private String contactMail;
	/**
	 * 销售备注
	 */
	private String saleRemark;
	
	/**
	 * 订单创建类型. API
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long createType;

	/**
	 * 出票时间
	 */
	private Date issueTime;
	/**
	* 航程集合
	* */
	private List<IftLeg> legList;
	/**
	* 乘客集合
	* */
	private List<IftApiPassenger> passengerList;

	//订单状态：待核价（1），已核价（2），出票中（3），已出票（4），已取消（5）  属于主订单的状态
	/**
	* 销售单明细
	* */
	private List<IftSaleOrderDetail> saleOrderDetailList;

	public Long getSaleOrderNo() {
		return saleOrderNo;
	}

	public void setSaleOrderNo(Long saleOrderNo) {
		this.saleOrderNo = saleOrderNo;
	}

	public SaleOrder getSaleOrder() {
		return saleOrder;
	}

	public void setSaleOrder(SaleOrder saleOrder) {
		this.saleOrder = saleOrder;
	}

	public Long getDemandNo() {
		return demandNo;
	}

	public void setDemandNo(Long demandNo) {
		this.demandNo = demandNo;
	}

	public IftDemand getDemand() {
		return demand;
	}

	public void setDemand(IftDemand demand) {
		this.demand = demand;
	}

	public Integer getLegType() {
		return legType;
	}

	public void setLegType(Integer legType) {
		this.legType = legType;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getContactMail() {
		return contactMail;
	}

	public void setContactMail(String contactMail) {
		this.contactMail = contactMail;
	}

	public String getSaleRemark() {
		return saleRemark;
	}

	public void setSaleRemark(String saleRemark) {
		this.saleRemark = saleRemark;
	}

	public Long getCreateType() {
		return createType;
	}

	public void setCreateType(Long createType) {
		this.createType = createType;
	}

	public Date getIssueTime() {
		return issueTime;
	}

	public void setIssueTime(Date issueTime) {
		this.issueTime = issueTime;
	}

	public List<IftLeg> getLegList() {
		return legList;
	}

	public void setLegList(List<IftLeg> legList) {
		this.legList = legList;
	}

	public List<IftApiPassenger> getPassengerList() {
		return passengerList;
	}

	public void setPassengerList(List<IftApiPassenger> passengerList) {
		this.passengerList = passengerList;
	}

	public List<IftSaleOrderDetail> getSaleOrderDetailList() {
		return saleOrderDetailList;
	}

	public void setSaleOrderDetailList(List<IftSaleOrderDetail> saleOrderDetailList) {
		this.saleOrderDetailList = saleOrderDetailList;
	}

	
}
