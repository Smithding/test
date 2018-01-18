package com.tempus.gss.product.ift.api.entity;


/**
 * <pre>
 * <b>产品批量 上传信息</b>
 * <b>Description:</b> 
 * </pre>
 */
public class PolicyIftBatch extends EntityIft {

	/** SVUID */
	private static final long serialVersionUID = 1L;

	/** 批次编号 业务主键 */
	protected String num;

	/** 产品类型 */
	protected int policyType;

	/** 批次名称 上传文件名称 */
	protected String name;

	/** 归档路径 */
	protected String sotrePath;

	/** 备注 */
	protected String remark;

	/** 状态 0 禁用；1 启用； 默认为禁用 */
	protected int status;

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public int getPolicyType() {
		return policyType;
	}

	public void setPolicyType(int policyType) {
		this.policyType = policyType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSotrePath() {
		return sotrePath;
	}

	public void setSotrePath(String sotrePath) {
		this.sotrePath = sotrePath;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Batch [id=" + id + ", owner=" + owner + ", num=" + num + ", policyType=" + policyType + ", name=" + name + ", sotrePath=" + sotrePath
				+ ", remark=" + remark + ", status=" + status + ", creator=" + creator + ", createTime=" + createTime + ", modifier=" + modifier
				+ ", modifyTime=" + modifyTime + "]";
	}

}
