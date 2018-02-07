package com.tempus.gss.product.ift.api.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;

import java.io.Serializable;
import java.util.Date;

public class Note implements Serializable {

	/**
	 * 签注编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long noteNo;

	/**
	 * ID
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long id;

	/**
	 * 数据归属单位
	 */
	private Integer owner;

	/**
	 * 类型 1:机票订单; 2:退废订单; 3:改签订单
	 */
	private Byte noteType;

	/**
	 * 订单编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long orderNo;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 操作人 默认为：sys
	 */
	private String modifier;

	/**
	 * 创建人
	 */
	private String creator;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	/**
	 * 删除标志 0 无效 已删除 1 有效
	 */
	private Byte valid;

	private static final long serialVersionUID = 1L;

	public Long getNoteNo() {

		return noteNo;
	}

	public void setNoteNo(Long noteNo) {

		this.noteNo = noteNo;
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

	public Byte getNoteType() {

		return noteType;
	}

	public void setNoteType(Byte noteType) {

		this.noteType = noteType;
	}

	public Long getOrderNo() {

		return orderNo;
	}

	public void setOrderNo(Long orderNo) {

		this.orderNo = orderNo;
	}

	public String getContent() {

		return content;
	}

	public void setContent(String content) {

		this.content = content;
	}

	public String getModifier() {

		return modifier;
	}

	public void setModifier(String modifier) {

		this.modifier = modifier;
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
}