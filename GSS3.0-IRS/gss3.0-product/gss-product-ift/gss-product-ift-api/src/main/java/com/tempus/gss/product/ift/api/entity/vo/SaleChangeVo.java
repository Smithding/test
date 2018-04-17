package com.tempus.gss.product.ift.api.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2016/11/14.
 */
public class SaleChangeVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *  该签单编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long saleChangeNo;

	private Date createTime;

	/**
	 * 客户办理状态 1.待审核 2.已审核 10.已退款 11.已取消
	 */
	private Integer saleStatus;

	/**
	 * 采购办理状态 1.待处理 2.处理中（已提交航司） 10.已退款 11.已拒单
	 */
	private Integer buyStatus;

	/**
	 * 航司办理状态 1.待审核 2.已审核 3.已退款 4.已拒单
	 */
	private Integer alineStatus;

	/**
	 * 票证类型
	 */
	private String ticketType;

	/**
	 * pnr
	 */
	private String pnr;

	/**
	 * 票号
	 */
	private String ticketNo;

	/**
	 * 客户手续费
	 */
	private String customerPrice;

	/**
	 * 乘机人
	 */
	private String passenger;

	/**
	 * 行程
	 */
	private String  leg;

	/**
	 * 航班号
	 */
	private String alineNo;

	/**
	 * 起飞时间
	 */
	private String startTime;

	/**
	 * 政策来源
	 */
	private String policySource;

	/**
	 * 订单来源
	 */
	private String orderSource;

	/**
	 * 公司名称
	 */
	private String companyName;
	
	/**
	 * 将销售单锁定的用户的Id 有大于0的值，表示已被用户锁定，是该用户的Id.
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long locker;

	/**
	 * 审核人员
	 */
	private String reviewName;

	/**
	 * 审核时间
	 */
	private String reviewTime;
	
	/**
	 * 废退费用
	 */
	private BigDecimal cost;
	
	/**
	 * 
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long agentId;
	
	/**
	 * 变更原因
	 */
	private String changeReson;
	
	/**
	 * 用户备注
	 */
	private String customerRemark;
	
	/** 交易单编号 */
	@JsonSerialize(using = LongSerializer.class)
	private Long transationOrderNo; 
	
	/** 销售单编号 */
	@JsonSerialize(using = LongSerializer.class)
	private Long saleOrderNo;
	
	
	/**
	 * 提交国际退款申请状态 0新增  1待处理  2 修改   3销售审核不通过  4销售算审核通过  5结算审核通过  6结算审核不通过
	 */
	private Integer refundStatus;

	//操作人
	private String handlers;

	public String getHandlers() {
		return handlers;
	}

	public void setHandlers(String handlers) {
		this.handlers = handlers;
	}

	public Long getSaleOrderNo() {
		return saleOrderNo;
	}

	public void setSaleOrderNo(Long saleOrderNo) {
		this.saleOrderNo = saleOrderNo;
	}

	public Date getCreateTime() {

		return createTime;
	}

	public void setCreateTime(Date createTime) {

		this.createTime = createTime;
	}

	public Integer getSaleStatus() {

		return saleStatus;
	}

	public void setSaleStatus(Integer saleStatus) {

		this.saleStatus = saleStatus;
	}

	public Integer getBuyStatus() {

		return buyStatus;
	}

	public void setBuyStatus(Integer buyStatus) {

		this.buyStatus = buyStatus;
	}

	public Integer getAlineStatus() {

		return alineStatus;
	}

	public void setAlineStatus(Integer alineStatus) {

		this.alineStatus = alineStatus;
	}

	public String getPnr() {

		return pnr;
	}

	public void setPnr(String pnr) {

		this.pnr = pnr;
	}

	public String getTicketNo() {

		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {

		this.ticketNo = ticketNo;
	}

	public String getCustomerPrice() {

		return customerPrice;
	}

	public void setCustomerPrice(String customerPrice) {

		this.customerPrice = customerPrice;
	}

	public String getPassenger() {

		return passenger;
	}

	public void setPassenger(String passenger) {

		this.passenger = passenger;
	}

	public String getLeg() {

		return leg;
	}

	public void setLeg(String leg) {

		this.leg = leg;
	}

	public String getAlineNo() {

		return alineNo;
	}

	public void setAlineNo(String alineNo) {

		this.alineNo = alineNo;
	}

	public String getStartTime() {

		return startTime;
	}

	public void setStartTime(String startTime) {

		this.startTime = startTime;
	}

	public String getPolicySource() {

		return policySource;
	}

	public void setPolicySource(String policySource) {

		this.policySource = policySource;
	}

	public String getOrderSource() {

		return orderSource;
	}

	public void setOrderSource(String orderSource) {

		this.orderSource = orderSource;
	}

	public String getCompanyName() {

		return companyName;
	}

	public void setCompanyName(String companyName) {

		this.companyName = companyName;
	}

	public String getReviewName() {

		return reviewName;
	}

	public void setReviewName(String reviewName) {

		this.reviewName = reviewName;
	}

	public String getReviewTime() {
		return reviewTime;
	}

	public void setReviewTime(String reviewTime) {
		this.reviewTime = reviewTime;
	}

	

	public String getTicketType() {
		return ticketType;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}

	public Long getSaleChangeNo() {

		return saleChangeNo;
	}

	public void setSaleChangeNo(Long saleChangeNo) {

		this.saleChangeNo = saleChangeNo;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public Long getAgentId() {
		return agentId;
	}

	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}

	public Long getLocker() {
		return locker;
	}

	public void setLocker(Long locker) {
		this.locker = locker;
	}

	public Long getTransationOrderNo() {
		return transationOrderNo;
	}

	public void setTransationOrderNo(Long transationOrderNo) {
		this.transationOrderNo = transationOrderNo;
	}

	public String getChangeReson() {
		return changeReson;
	}

	public void setChangeReson(String changeReson) {
		this.changeReson = changeReson;
	}

	public String getCustomerRemark() {
		return customerRemark;
	}

	public void setCustomerRemark(String customerRemark) {
		this.customerRemark = customerRemark;
	}

	public Integer getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
	}
	
	
}
