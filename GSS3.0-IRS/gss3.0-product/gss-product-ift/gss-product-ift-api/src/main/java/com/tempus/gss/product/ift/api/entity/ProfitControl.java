package com.tempus.gss.product.ift.api.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 每个舱位渠道控润信息.
 */
public class ProfitControl implements Serializable {

	@JsonSerialize(using = LongSerializer.class)
	private Long profitControlNo;

	private Long id;

	/**
	 * 政策的舱位.
	 */
	private Long policyCabinNo;

	/**
	 * 控润渠道类型，目前可用范围：301（分销商），302（集团客户）,303（散客会员）,306（体内销售）.
	 */
	private Long customerTypeNo;

	//<editor-fold desc="该渠道的销售与采购返点和手续费.">
	/**
	 * 销售返点，百分数，0.01表示一个点.
	 */
	private BigDecimal saleRebate;

	/**
	 * 销售单程手续费，5，表示￥5.
	 */
	private BigDecimal saleOwBrokerage;

	/**
	 * 销售往返手续费，5，表示￥5.
	 */
	private BigDecimal saleRtBrokerage;

	/**
	 * 采购返点，百分数，0.01表示一个点.
	 */
	private BigDecimal buyRebate;

	/**+
	 * 采购单程手续费，5，表示￥5.
	 */
	private BigDecimal buyOwBrokerage;

	/**
	 * 采购往返手续费，5，表示￥5.
	 */
	//</editor-fold>

	private BigDecimal buyRtBrokerage;

	public Long getProfitControlNo() {

		return profitControlNo;
	}

	public void setProfitControlNo(Long profitControlNo) {

		this.profitControlNo = profitControlNo;
	}

	public Long getId() {

		return id;
	}

	public void setId(Long id) {

		this.id = id;
	}

	public Long getPolicyCabinNo() {

		return policyCabinNo;
	}

	public void setPolicyCabinNo(Long policyCabinNo) {

		this.policyCabinNo = policyCabinNo;
	}

	public Long getCustomerTypeNo() {

		return customerTypeNo;
	}

	public void setCustomerTypeNo(Long customerTypeNo) {

		this.customerTypeNo = customerTypeNo;
	}

	public BigDecimal getSaleRebate() {

		return saleRebate;
	}

	public void setSaleRebate(BigDecimal saleRebate) {

		this.saleRebate = saleRebate;
	}

	public BigDecimal getSaleOwBrokerage() {

		return saleOwBrokerage;
	}

	public void setSaleOwBrokerage(BigDecimal saleOwBrokerage) {

		this.saleOwBrokerage = saleOwBrokerage;
	}

	public BigDecimal getSaleRtBrokerage() {

		return saleRtBrokerage;
	}

	public void setSaleRtBrokerage(BigDecimal saleRtBrokerage) {

		this.saleRtBrokerage = saleRtBrokerage;
	}

	public BigDecimal getBuyRebate() {

		return buyRebate;
	}

	public void setBuyRebate(BigDecimal buyRebate) {

		this.buyRebate = buyRebate;
	}

	public BigDecimal getBuyOwBrokerage() {

		return buyOwBrokerage;
	}

	public void setBuyOwBrokerage(BigDecimal buyOwBrokerage) {

		this.buyOwBrokerage = buyOwBrokerage;
	}

	public BigDecimal getBuyRtBrokerage() {

		return buyRtBrokerage;
	}

	public void setBuyRtBrokerage(BigDecimal buyRtBrokerage) {

		this.buyRtBrokerage = buyRtBrokerage;
	}


}
