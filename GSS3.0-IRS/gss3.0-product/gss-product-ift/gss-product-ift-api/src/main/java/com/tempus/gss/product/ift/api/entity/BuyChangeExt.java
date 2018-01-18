package com.tempus.gss.product.ift.api.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.order.entity.BuyChange;
import com.tempus.gss.serializer.LongSerializer;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 采购变更单扩展.
 * 包含改签，废票、退票。
 */
public class BuyChangeExt implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * Id
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long id;
	/**
	 * 采购单废退改编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long buyChangeNo;
	/**
	 * 采购改签单
	 */
	private BuyChange buyChange;
	/**
	 * 业务类型 1废 2退 3改
	 * 同(BuyChange.orderChangeType)
	 */
	private Integer orderChangeType;
	/**
	 * 数据归属单位
	 */
	private Integer owner;
	/**
	 * 出票office
	 */
	private String office;
	/**
	 * 出票类型
	 */
	private Integer ticketType;
	/**
	 * 拒单理由.
	 */
	private String refuseReason;
	/**
	 * 改签时采购的Pnr列表.
	 */
	private List<Pnr> pnrList;
	/**
	 * 创建人
	 */
	private String creator;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 操作人
	 */
	private String modifier;
	/**
	 * 修改日期
	 */
	private Date modifyTime;
	/**
	 * 删除标志  0 无效 已删除 1 有效
	 */
	private Byte valid;
	/**
	 *
	 */
	private String status;

	public Long getBuyChangeNo() {

		return buyChangeNo;
	}

	public void setBuyChangeNo(Long buyChangeNo) {

		this.buyChangeNo = buyChangeNo;
	}

	public BuyChange getBuyChange() {

		return buyChange;
	}

	public void setBuyChange(BuyChange buyChange) {

		this.buyChange = buyChange;
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

	public String getOffice() {

		return office;
	}

	public void setOffice(String office) {

		this.office = office;
	}

	public Integer getTicketType() {

		return ticketType;
	}

	public void setTicketType(Integer ticketType) {

		this.ticketType = ticketType;
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

	public String getModifier() {

		return modifier;
	}

	public void setModifier(String modifier) {

		this.modifier = modifier;
	}

	public Date getModifyTime() {

		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {

		this.modifyTime = modifyTime;
	}

	public Byte getValid() {

		return valid;
	}

	public void setValid(Byte valid) {

		this.valid = valid;
	}

	public String getStatus() {

		return status;
	}

	public void setStatus(String status) {

		this.status = status;
	}

	public Integer getOrderChangeType() {
		return orderChangeType;
	}

	public void setOrderChangeType(Integer orderChangeType) {
		this.orderChangeType = orderChangeType;
	}

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public List<Pnr> getPnrList() {
		return pnrList;
	}

	public void setPnrList(List<Pnr> pnrList) {
		this.pnrList = pnrList;
	}
	
	
}