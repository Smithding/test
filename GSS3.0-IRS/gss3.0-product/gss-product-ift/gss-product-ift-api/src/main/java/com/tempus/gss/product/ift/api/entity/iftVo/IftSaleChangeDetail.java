package com.tempus.gss.product.ift.api.entity.iftVo;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;

public class IftSaleChangeDetail implements Serializable {
	/**
	 * 销售该签单明细编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long saleChangeDetailNo;

	/**
	 * 销售单变更编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long saleChangeNo;

	/**
	 * 销售单明细编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long saleOrderDetailNo;

	/**
	 * 改签的销售明细类型. 如果是废退，就和这里的值无关，所有的明细都是需要废退的。1:被改掉的（旧的）航段.*2:新增的航段.
	 */
	private Integer orderDetailType;

	/**
	 * 销售单明细.
	 */
	private IftSaleOrderDetail saleOrderDetail;


	private static final long serialVersionUID = 1L;

	public Long getSaleChangeDetailNo() {
		return saleChangeDetailNo;
	}

	public void setSaleChangeDetailNo(Long saleChangeDetailNo) {
		this.saleChangeDetailNo = saleChangeDetailNo;
	}


	public Long getSaleChangeNo() {
		return saleChangeNo;
	}

	public void setSaleChangeNo(Long saleChangeNo) {
		this.saleChangeNo = saleChangeNo;
	}

	public Long getSaleOrderDetailNo() {
		return saleOrderDetailNo;
	}

	public void setSaleOrderDetailNo(Long saleOrderDetailNo) {
		this.saleOrderDetailNo = saleOrderDetailNo;
	}

	public Integer getOrderDetailType() {
		return orderDetailType;
	}

	public void setOrderDetailType(Integer orderDetailType) {
		this.orderDetailType = orderDetailType;
	}

	public IftSaleOrderDetail getSaleOrderDetail() {
		return saleOrderDetail;
	}

	public void setSaleOrderDetail(IftSaleOrderDetail saleOrderDetail) {
		this.saleOrderDetail = saleOrderDetail;
	}

	
}