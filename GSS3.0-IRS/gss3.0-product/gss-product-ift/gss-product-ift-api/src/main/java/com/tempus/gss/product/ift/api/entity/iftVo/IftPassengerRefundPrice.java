package com.tempus.gss.product.ift.api.entity.iftVo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 乘客废退明细，表示乘客废退是的销售价格和采购价格。
 */
public class IftPassengerRefundPrice implements Serializable {

	/**
	 * 退票编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long passengerRefundPriceNo;

	/**
	 * 销售单编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long saleOrderNo;

	/**
	 * 销售改签单编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long saleChangeNo;

	/**
	 * 乘客编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long passengerNo;

	/**
	 * 乘客
	 */
	private IftApiPassenger passenger;

	/**
	 * 销售票面价.
	 */
	private BigDecimal salePrice;

	/**
	 * 销售税费
	 */
	private BigDecimal saleTax;

	/**
	 * 退票手续费
	 */
	private BigDecimal saleRefundPrice;

	 /**
     * 销售机票结算价
     */
    private BigDecimal saleTicketAccount;

    /**
     * 销售回收奖励
     */
    private BigDecimal saleRecoveryAward;

    /**
     * 销售退票结算价
     */
    private BigDecimal saleRefundAccount;

	/**
     * 1:成人 2：儿童 3：婴儿
     */
    private String passengerType;

    /**
     * 1:销售 2：采购
     */
    private String orderType;

	/**
	 * 票号
	 */
	private String ticketNo;

	/**
	 * 销售合计
	 */
	private BigDecimal saleCount;
	/**
	 * 采购服务费
	 */
	private BigDecimal saleBrokerage;

	private static final long serialVersionUID = 1L;

	public Long getPassengerRefundPriceNo() {
		return passengerRefundPriceNo;
	}

	public void setPassengerRefundPriceNo(Long passengerRefundPriceNo) {
		this.passengerRefundPriceNo = passengerRefundPriceNo;
	}

	public Long getSaleOrderNo() {
		return saleOrderNo;
	}

	public void setSaleOrderNo(Long saleOrderNo) {
		this.saleOrderNo = saleOrderNo;
	}

	public Long getSaleChangeNo() {
		return saleChangeNo;
	}

	public void setSaleChangeNo(Long saleChangeNo) {
		this.saleChangeNo = saleChangeNo;
	}

	public Long getPassengerNo() {
		return passengerNo;
	}

	public void setPassengerNo(Long passengerNo) {
		this.passengerNo = passengerNo;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public BigDecimal getSaleTax() {
		return saleTax;
	}

	public void setSaleTax(BigDecimal saleTax) {
		this.saleTax = saleTax;
	}

	public BigDecimal getSaleRefundPrice() {
		return saleRefundPrice;
	}

	public void setSaleRefundPrice(BigDecimal saleRefundPrice) {
		this.saleRefundPrice = saleRefundPrice;
	}

	public BigDecimal getSaleTicketAccount() {
		return saleTicketAccount;
	}

	public void setSaleTicketAccount(BigDecimal saleTicketAccount) {
		this.saleTicketAccount = saleTicketAccount;
	}

	public BigDecimal getSaleRecoveryAward() {
		return saleRecoveryAward;
	}

	public void setSaleRecoveryAward(BigDecimal saleRecoveryAward) {
		this.saleRecoveryAward = saleRecoveryAward;
	}

	public BigDecimal getSaleRefundAccount() {
		return saleRefundAccount;
	}

	public void setSaleRefundAccount(BigDecimal saleRefundAccount) {
		this.saleRefundAccount = saleRefundAccount;
	}

	public String getPassengerType() {
		return passengerType;
	}

	public void setPassengerType(String passengerType) {
		this.passengerType = passengerType;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public BigDecimal getSaleBrokerage() {
		return saleBrokerage;
	}

	public void setSaleBrokerage(BigDecimal saleBrokerage) {
		this.saleBrokerage = saleBrokerage;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public BigDecimal getSaleCount() {
		return saleCount;
	}

	public void setSaleCount(BigDecimal saleCount) {
		this.saleCount = saleCount;
	}

	public IftApiPassenger getPassenger() {
		return passenger;
	}

	public void setPassenger(IftApiPassenger passenger) {
		this.passenger = passenger;
	}
	
	
}