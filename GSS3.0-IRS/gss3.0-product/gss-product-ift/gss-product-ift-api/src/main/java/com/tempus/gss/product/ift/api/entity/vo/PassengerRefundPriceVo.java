package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.product.ift.api.entity.Passenger;
import com.tempus.gss.product.ift.api.entity.PassengerRefundPrice;
import com.tempus.gss.serializer.LongSerializer;

/**
 * 乘客废退明细，表示乘客废退是的销售价格和采购价格。
 */
public class PassengerRefundPriceVo implements Serializable {
	private static final long serialVersionUID = -2089025192865870487L;

	/**
	 * 
	 */

	
	/**
	 * 
	 */
	public List<PassengerRefundPrice> passengerRefundPriceList;
	
	/**
	 * 变更单NO
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long saleChangeNo;
	
	public List<Passenger> passenger;
	
	/**
	 * 销售单编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long saleOrderNo;
	
	/**
	 * 拒单原因
	 */
	private String reason;
	
	/**
	 * 1:提交航司退款 2：航司已审核通过 3：航司已退款 5：确认并退款 4：拒单
	 */
	private String status;
	
	/**
	 * 1跳转废退审核页面  2 跳转废退核价页面  3废退审核按钮
	 */
	private String pageStatus;
	
	/**
	 * 退废类型 1自愿 2非自愿
	 * @return
	 */
	private Integer refundWay;

	/**
	 * 0是采购 1是销售
	 */
	private Integer todoType;

	//采购币种
	private String currency;
	//销售币种
    private String saleCurrency;
	//汇率
	private BigDecimal exchangeRate;
	//订单来源
	private String sourceChannelNo;

	public List<PassengerRefundPrice> getPassengerRefundPriceList() {
		return passengerRefundPriceList;
	}

	public void setPassengerRefundPriceList(List<PassengerRefundPrice> passengerRefundPriceList) {
		this.passengerRefundPriceList = passengerRefundPriceList;
	}

	public Long getSaleChangeNo() {
		return saleChangeNo;
	}

	public void setSaleChangeNo(Long saleChangeNo) {
		this.saleChangeNo = saleChangeNo;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Long getSaleOrderNo() {
		return saleOrderNo;
	}

	public void setSaleOrderNo(Long saleOrderNo) {
		this.saleOrderNo = saleOrderNo;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Passenger> getPassenger() {
		return passenger;
	}

	public void setPassenger(List<Passenger> passenger) {
		this.passenger = passenger;
	}

	public String getPageStatus() {
		return pageStatus;
	}

	public void setPageStatus(String pageStatus) {
		this.pageStatus = pageStatus;
	}

	public Integer getRefundWay() {
		return refundWay;
	}

	public void setRefundWay(Integer refundWay) {
		this.refundWay = refundWay;
	}

	public Integer getTodoType() {
		return todoType;
	}

	public void setTodoType(Integer todoType) {
		this.todoType = todoType;
	}

	public String getSaleCurrency() {
		return saleCurrency;
	}

	public void setSaleCurrency(String saleCurrency) {
		this.saleCurrency = saleCurrency;
	}

	public String getSourceChannelNo() {
		return sourceChannelNo;
	}

	public void setSourceChannelNo(String sourceChannelNo) {
		this.sourceChannelNo = sourceChannelNo;
	}
}