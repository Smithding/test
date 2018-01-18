package com.tempus.gss.product.ift.api.entity.iftVo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;

import java.io.Serializable;
import java.math.BigDecimal;

public class IftPassengerChangePrice implements Serializable {

	/**
	 * 改签编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long changePriceNo;

	/**
	 * 销售改签单编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long saleChangeNo;

	/**
	 * 销售单编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long saleOrderNo;

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
	 * 销售差价.
	 */
	private BigDecimal salePrice;
	/**
	 * 乘客类型
	 */
	private String passengerType;
	/**
	 * 订单类型
	 * 业务类型 1废 2退 3改 (同SaleChange的 orderChangeType 属性)
	 */
	private String orderType;
	
	/**
	 * 销售税费
	 */
	private BigDecimal saleTax;

	/**
	 * 销售手续费
	 */
	private BigDecimal saleBrokerage;

	/**
	 * 销售其他费用
	 */
	private BigDecimal saleRest;
	/**
     * 改签结算价
     */
    private BigDecimal saleCountPrice;

	private static final long serialVersionUID = 1L;
	
	public Long getChangePriceNo() {
	
		return changePriceNo;
	}
	
	public void setChangePriceNo(Long changePriceNo) {
	
		this.changePriceNo = changePriceNo;
	}

	public Long getSaleChangeNo() {

		return saleChangeNo;
	}

	public void setSaleChangeNo(Long saleChangeNo) {

		this.saleChangeNo = saleChangeNo;
	}

	public Long getSaleOrderNo() {

		return saleOrderNo;
	}

	public void setSaleOrderNo(Long saleOrderNo) {

		this.saleOrderNo = saleOrderNo;
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

	public BigDecimal getSaleBrokerage() {

		return saleBrokerage;
	}

	public void setSaleBrokerage(BigDecimal saleBrokerage) {

		this.saleBrokerage = saleBrokerage;
	}

	public BigDecimal getSaleRest() {

		return saleRest;
	}

	public void setSaleRest(BigDecimal saleRest) {

		this.saleRest = saleRest;
	}


	public BigDecimal getSaleCountPrice() {
	
		return saleCountPrice;
	}
	
	public void setSaleCountPrice(BigDecimal saleCountPrice) {
	
		this.saleCountPrice = saleCountPrice;
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

	public IftApiPassenger getPassenger() {
		return passenger;
	}

	public void setPassenger(IftApiPassenger passenger) {
		this.passenger = passenger;
	}

}