package com.tempus.gss.product.ift.api.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.product.ift.api.entity.BuyOrderExt;
import com.tempus.gss.product.ift.api.entity.SaleOrderExt;
import com.tempus.gss.serializer.LongSerializer;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/10/12.
 */
public class OrderCreateVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SaleOrderExt saleOrderExt;

	public BuyOrderExt buyOrderExt;
	
	public List<OrderPriceVo> orderPriceVoList;

	/*
	* 客户类型
	* */
	@JsonSerialize(using = LongSerializer.class)
	public Long customerTypeNo;

	/*
	* 客户编号
	* */
	@JsonSerialize(using = LongSerializer.class)
	public Long customerNo;
	
	/**
	 * 出票配置
	 */
	private String ticketConfig;

	/**
	 * 出票航司
	 */
	private String ticketAirline;

	/**
	 * 出票类型
	 */
	private String ticketType;
	
	/**
	 * 是否线上生成PNR 1 创建PNR   2 提取PNR
	 */
	private String newPNR;
	
	private String remark;
	
	private String isDespoit;
	/**
	 * 原始订单号
	 */
	private Long originalOrderNo;

	public SaleOrderExt getSaleOrderExt() {

		return saleOrderExt;
	}

	public void setSaleOrderExt(SaleOrderExt saleOrderExt) {

		this.saleOrderExt = saleOrderExt;
	}

	public BuyOrderExt getBuyOrderExt() {

		return buyOrderExt;
	}

	public void setBuyOrderExt(BuyOrderExt buyOrderExt) {

		this.buyOrderExt = buyOrderExt;
	}

	public String getNewPNR() {
		return newPNR;
	}

	public void setNewPNR(String newPNR) {
		this.newPNR = newPNR;
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

	public List<OrderPriceVo> getOrderPriceVoList() {
		return orderPriceVoList;
	}

	public void setOrderPriceVoList(List<OrderPriceVo> orderPriceVoList) {
		this.orderPriceVoList = orderPriceVoList;
	}

	public String getTicketConfig() {
		return ticketConfig;
	}

	public void setTicketConfig(String ticketConfig) {
		this.ticketConfig = ticketConfig;
	}

	public String getTicketAirline() {
		return ticketAirline;
	}

	public void setTicketAirline(String ticketAirline) {
		this.ticketAirline = ticketAirline;
	}

	public String getTicketType() {
		return ticketType;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsDespoit() {
		return isDespoit;
	}

	public void setIsDespoit(String isDespoit) {
		this.isDespoit = isDespoit;
	}

	public Long getOriginalOrderNo() {
		return originalOrderNo;
	}

	public void setOriginalOrderNo(Long originalOrderNo) {
		this.originalOrderNo = originalOrderNo;
	}
}
