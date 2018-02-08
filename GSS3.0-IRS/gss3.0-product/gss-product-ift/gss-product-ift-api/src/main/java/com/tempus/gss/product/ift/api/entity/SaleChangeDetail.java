package com.tempus.gss.product.ift.api.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;

public class SaleChangeDetail implements Serializable {
	/**
	 * 销售该签单明细编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long saleChangeDetailNo;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 数据归属单位
	 */
	private Integer owner;

	/**
	 * 销售单变更编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long saleChangeNo;

	/**
	 * 采购单变更编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long buyChangeNo;

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
	 * 创建人
	 */
	private String creator;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 删除标志 0 删除 1有效
	 */
	private Byte valid;

	/**
	 * 销售单明细.
	 */
	private SaleOrderDetail saleOrderDetail;

	/**
	 * 票号
	 */
	private String ticketNo;


	private static final long serialVersionUID = 1L;

	public Long getSaleChangeDetailNo() {
		return saleChangeDetailNo;
	}

	public void setSaleChangeDetailNo(Long saleChangeDetailNo) {
		this.saleChangeDetailNo = saleChangeDetailNo;
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

	public Long getSaleChangeNo() {
		return saleChangeNo;
	}

	public void setSaleChangeNo(Long saleChangeNo) {
		this.saleChangeNo = saleChangeNo;
	}

	public Long getBuyChangeNo() {
		return buyChangeNo;
	}

	public void setBuyChangeNo(Long buyChangeNo) {
		this.buyChangeNo = buyChangeNo;
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

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Byte getValid() {
		return valid;
	}

	public void setValid(Byte valid) {
		this.valid = valid;
	}

	public SaleOrderDetail getSaleOrderDetail() {

		return saleOrderDetail;
	}

	public void setSaleOrderDetail(SaleOrderDetail saleOrderDetail) {

		this.saleOrderDetail = saleOrderDetail;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}
}