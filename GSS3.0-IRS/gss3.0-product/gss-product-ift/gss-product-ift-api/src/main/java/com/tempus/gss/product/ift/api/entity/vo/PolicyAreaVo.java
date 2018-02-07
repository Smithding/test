package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;

/**
 * * ClassName:PnrAreaVo * Function: TODO ADD FUNCTION * Reason: TODO ADD REASON
 * * * @author Administrator * @version * @since Ver 1.1 * @Date 2016年10月14日
 * 上午10:56:53 * *
 *
 * @see *
 */
public class PolicyAreaVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 自定义区域编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long policyAreaNo;

	/**
	 * 编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long id;

	/**
	 * 归属单位
	 */
	private Integer owner;

	/**
	 * 区域名称
	 */
	private String areaName;

	/**
	 * POLICY代号
	 */
	private String areaCode;

	/**
	 * 删除标记
	 */
	private Boolean valid;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	/**
	 * 创建者
	 */
	private String creator;

	/**
	 * 修改者
	 */
	private String modifier;

	public Long getPolicyAreaNo() {

		return policyAreaNo;
	}

	public void setPolicyAreaNo(Long policyAreaNo) {

		this.policyAreaNo = policyAreaNo;
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

	public String getAreaName() {

		return areaName;
	}

	public void setAreaName(String areaName) {

		this.areaName = areaName;
	}

	public String getAreaCode() {

		return areaCode;
	}

	public void setAreaCode(String areaCode) {

		this.areaCode = areaCode;
	}

	public Boolean getValid() {

		return valid;
	}

	public void setValid(Boolean valid) {

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

	public String getModifier() {

		return modifier;
	}

	public void setModifier(String modifier) {

		this.modifier = modifier;
	}

	@Override
	public String toString() {

		return "PolicyArea [policyAreaNo=" + policyAreaNo + ", id=" + id + ", owner=" + owner + ", areaName=" + areaName
				+ ", areaCode=" + areaCode + ", valid=" + valid + ", createTime=" + createTime + ", modifyTime="
				+ modifyTime + ", creator=" + creator + ", modifier=" + modifier + "]";
	}

}
