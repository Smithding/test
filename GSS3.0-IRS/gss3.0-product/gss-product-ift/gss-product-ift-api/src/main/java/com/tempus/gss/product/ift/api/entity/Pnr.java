package com.tempus.gss.product.ift.api.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;

import java.io.Serializable;
import java.util.Date;

/**
 * pnr表.
 */
public class Pnr implements Serializable {

	/**
	 * PNR编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long pnrNo;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 数据归属
	 */
	private Integer owner;

	/**
	 * 订单来源编号 可能为采购单编号，也可能为销售单编号，具体根据pnrSource确定
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long sourceNo;

	/**
	 * 航司PNR编号
	 */
	private String pnr;

	/**
	 * pnr编码类型.
	 * 1：ETERM，2：ABACUS，3：GALILEO，4：SABRE，5：AMADEUS，6：WORLDSPAN
	 */
	private Integer pnrType;

	/**
	 * PNR内容
	 */
	private String pnrContent;

	/**
	 * Pnr大编码 6位字符串.
	 */
	private String bigPnr;

	/**
	 * RT时间
	 */
	private Date rtTime;

	/**
	 * PNR来源 1：导入，2：采购，3：改签，4：手动生成
	 */
	private Integer pnrSource;

	/**
	 * 操作人 默认为：sys
	 */
	private String modifier;

	/**
	 * 删除标志 0 无效 已删除 1 有效
	 */
	private Byte valid;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	/**
	 * 创建人
	 */
	private String creator;

	/**
	 * PNR状态 1.已创建 2.已出票 3.已取消
	 */
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	private static final long serialVersionUID = 1L;

	public Long getPnrNo() {

		return pnrNo;
	}

	public void setPnrNo(Long pnrNo) {

		this.pnrNo = pnrNo;
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

	public Long getSourceNo() {

		return sourceNo;
	}

	public void setSourceNo(Long sourceNo) {

		this.sourceNo = sourceNo;
	}

	public String getPnr() {

		return pnr;
	}

	public void setPnr(String pnr) {

		this.pnr = pnr;
	}

	public Integer getPnrType() {

		return pnrType;
	}

	public void setPnrType(Integer pnrType) {

		this.pnrType = pnrType;
	}

	public String getPnrContent() {

		return pnrContent;
	}

	public void setPnrContent(String pnrContent) {

		this.pnrContent = pnrContent;
	}

	public String getBigPnr() {

		return bigPnr;
	}

	public void setBigPnr(String bigPnr) {

		this.bigPnr = bigPnr;
	}

	public Date getRtTime() {

		return rtTime;
	}

	public void setRtTime(Date rtTime) {

		this.rtTime = rtTime;
	}

	public Integer getPnrSource() {

		return pnrSource;
	}

	public void setPnrSource(Integer pnrSource) {

		this.pnrSource = pnrSource;
	}

	public String getModifier() {

		return modifier;
	}

	public void setModifier(String modifier) {

		this.modifier = modifier;
	}

	public Byte getValid() {

		return valid;
	}

	public void setValid(Byte valid) {

		this.valid = valid;
	}

	public Date getCreateTime() {

		return createTime;
	}

	public void setCreateTime(Date createTime) {

		this.createTime = createTime;
	}

	public Date getModifyTime() {

		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {

		this.modifyTime = modifyTime;
	}

	public String getCreator() {

		return creator;
	}

	public void setCreator(String creator) {

		this.creator = creator;
	}

	@Override
	public String toString() {

		return "Pnr [pnrNo=" + pnrNo + ", id=" + id + ", owner=" + owner + ", sourceNo=" + sourceNo + ", pnr=" + pnr
				+ ", pnrContent=" + pnrContent + ", bigPnr=" + bigPnr + ", rtTime=" + rtTime + ", pnrSource="
				+ pnrSource + ", modifier=" + modifier + ", valid=" + valid + ", createTime=" + createTime
				+ ", modifyTime=" + modifyTime + ", creator=" + creator + "]";
	}
}