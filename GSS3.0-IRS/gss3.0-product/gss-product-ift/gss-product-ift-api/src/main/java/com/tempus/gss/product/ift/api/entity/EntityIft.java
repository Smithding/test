package com.tempus.gss.product.ift.api.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;

/**
 * <pre>
 * <b>实体信息.</b>
 * <b>Description:</b>
 */
public abstract class EntityIft implements Serializable {

	/** SVUID */
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** ID, 物理主键 */
	@TableId(type = IdType.INPUT)
	@JsonSerialize(using = LongSerializer.class)
	protected Long id;

	/** 归集单位 */
	protected Integer owner;

	/** 创建者, 默认:sys */
	protected String creator;

	/** 创建时间 */
	protected Date createTime;

	/** 修改者, 默认:sys */
	protected String modifier;

	/** 修改时间 */
	protected Date modifyTime;


	/**
	 * 构造方法.
	 */
	protected EntityIft() {
		super();
	}

	/**
	 * 构造方法.
	 * 
	 * @param id
	 */
	public EntityIft(Long id) {
		this(id, null);
	}

	/**
	 * 构造方法.
	 * 
	 * @param id
	 * @param owner
	 */
	public EntityIft(Long id, Integer owner) {
		super();
		this.id = id;
		this.owner = owner;
	}

	/**
	 * 构造方法.
	 * 
	 * @param id
	 * @param owner
	 * @param creator
	 * @param createTime
	 * @param modifier
	 * @param modifyTime
	 */
	public EntityIft(Long id, Integer owner, String creator, Date createTime, String modifier, Date modifyTime) {
		super();
		this.id = id;
		this.owner = owner;
		this.creator = creator;
		this.createTime = createTime;
		this.modifier = modifier;
		this.modifyTime = modifyTime;
	}

	/** getter and setter */

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

	@Override
	public String toString() {
		return "Entity [id=" + id + ", owner=" + owner + ", creator=" + creator + ", createTime=" + createTime + ", modifier=" + modifier + ", modifyTime="
				+ modifyTime + "]";
	}

}
