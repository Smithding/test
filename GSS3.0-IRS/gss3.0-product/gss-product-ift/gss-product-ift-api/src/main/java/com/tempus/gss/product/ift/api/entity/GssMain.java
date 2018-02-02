package com.tempus.gss.product.ift.api.entity;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

public class GssMain implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** 主键 */
	@TableId(type = IdType.AUTO)
	private Long id;

	/** 数据归属单位 */
	private Integer owner;

	/** 销售公司 */
	private String saleCom;

	/** 结算公司 */
	private String accCom;

	/** 业务线 */
	private String line;

	/** 销售部门 */
	private String saleDept;

	/** 客户类型 */
	private String cusType;

	/** 客户名称 */
	private String cusName;

	/** 客户编码 */
	private String cusCode;

	/** 产品类型 */
	private String proType;

	/** 销售订单号 */
	private String saleOrdNum;

	/** 帐单号 */
	private String billNum;

	/** 人数 */
	private String peoNum;

	/** 交易日期 */
	private String issueDate;

	/** 应收金额 */
	private BigDecimal receAmount;

	/** 实收金额 */
	private BigDecimal actAmount;

	/** 结算方式 */
	private String settlMethod;

	/** 收款方式 */
	private String paidMethod;

	/** 帐号 */
	private String accounts;

	/** 收款流水号 */
	private String receNum;

	/** 供应商名称 */
	private String supplierName;

	/** 实付金额 */
	private BigDecimal paidAmount;

	/** 支付状态 */
	private String paidStatus;

	/** 订单状态 */
	private String orderStatus;

	/** 备注 */
	private String remarks;

	/** 创建时间 */
	private Date createTime;

	/** 修改时间 */
	private Date modifyTime;

	/** 操作人 */
	private String modifier;

	/** 上传状态 */
	private String uploadStatus;

	/** 上传错误原因说明 */
	private String errorCause;

	/** 上传时间 */
	private Date uploadTime;

	/** 删除标志 0 无效 已删除 1 有效 */
	private Boolean valid;

	/** 扩展字段1 */
	private String extend1;

	/** 扩展字段2 */
	private String extend2;

	/** 扩展字段3 */
	private String extend3;

	/** 扩展字段4 */
	private String extend4;

	/** 扩展字段5 */
	private String extend5;

	/** 扩展字段6 */
	private String extend6;

	/** 扩展字段7 */
	private String extend7;

	/** 扩展字段8 */
	private String extend8;

	/** 扩展字段9 */
	private String extend9;

	/** 扩展字段10 */
	private String extend10;

	/** 扩展字段11 */
	private String extend11;

	/** 扩展字段12 */
	private String extend12;

	/** 扩展字段13 */
	private String extend13;

	/** 扩展字段14 */
	private String extend14;

	/** 扩展字段15 */
	private String extend15;


	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getOwner() {
		return this.owner;
	}

	public void setOwner(Integer owner) {
		this.owner = owner;
	}

	public String getSaleCom() {
		return this.saleCom;
	}

	public void setSaleCom(String saleCom) {
		this.saleCom = saleCom;
	}

	public String getAccCom() {
		return this.accCom;
	}

	public void setAccCom(String accCom) {
		this.accCom = accCom;
	}

	public String getLine() {
		return this.line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getSaleDept() {
		return this.saleDept;
	}

	public void setSaleDept(String saleDept) {
		this.saleDept = saleDept;
	}

	public String getCusType() {
		return this.cusType;
	}

	public void setCusType(String cusType) {
		this.cusType = cusType;
	}

	public String getCusName() {
		return this.cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getCusCode() {
		return this.cusCode;
	}

	public void setCusCode(String cusCode) {
		this.cusCode = cusCode;
	}

	public String getProType() {
		return this.proType;
	}

	public void setProType(String proType) {
		this.proType = proType;
	}

	public String getSaleOrdNum() {
		return this.saleOrdNum;
	}

	public void setSaleOrdNum(String saleOrdNum) {
		this.saleOrdNum = saleOrdNum;
	}

	public String getBillNum() {
		return this.billNum;
	}

	public void setBillNum(String billNum) {
		this.billNum = billNum;
	}

	public String getPeoNum() {
		return this.peoNum;
	}

	public void setPeoNum(String peoNum) {
		this.peoNum = peoNum;
	}

	public String getIssueDate() {
		return this.issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	public BigDecimal getReceAmount() {
		return this.receAmount;
	}

	public void setReceAmount(BigDecimal receAmount) {
		this.receAmount = receAmount;
	}

	public BigDecimal getActAmount() {
		return this.actAmount;
	}

	public void setActAmount(BigDecimal actAmount) {
		this.actAmount = actAmount;
	}

	public String getSettlMethod() {
		return this.settlMethod;
	}

	public void setSettlMethod(String settlMethod) {
		this.settlMethod = settlMethod;
	}

	public String getPaidMethod() {
		return this.paidMethod;
	}

	public void setPaidMethod(String paidMethod) {
		this.paidMethod = paidMethod;
	}

	public String getAccounts() {
		return this.accounts;
	}

	public void setAccounts(String accounts) {
		this.accounts = accounts;
	}

	public String getReceNum() {
		return this.receNum;
	}

	public void setReceNum(String receNum) {
		this.receNum = receNum;
	}

	public String getSupplierName() {
		return this.supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public BigDecimal getPaidAmount() {
		return this.paidAmount;
	}

	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	public String getPaidStatus() {
		return this.paidStatus;
	}

	public void setPaidStatus(String paidStatus) {
		this.paidStatus = paidStatus;
	}

	public String getOrderStatus() {
		return this.orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getUploadStatus() {
		return this.uploadStatus;
	}

	public void setUploadStatus(String uploadStatus) {
		this.uploadStatus = uploadStatus;
	}

	public String getErrorCause() {
		return this.errorCause;
	}

	public void setErrorCause(String errorCause) {
		this.errorCause = errorCause;
	}

	public Date getUploadTime() {
		return this.uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public Boolean getValid() {
		return this.valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	public String getExtend1() {
		return this.extend1;
	}

	public void setExtend1(String extend1) {
		this.extend1 = extend1;
	}

	public String getExtend2() {
		return this.extend2;
	}

	public void setExtend2(String extend2) {
		this.extend2 = extend2;
	}

	public String getExtend3() {
		return this.extend3;
	}

	public void setExtend3(String extend3) {
		this.extend3 = extend3;
	}

	public String getExtend4() {
		return this.extend4;
	}

	public void setExtend4(String extend4) {
		this.extend4 = extend4;
	}

	public String getExtend5() {
		return this.extend5;
	}

	public void setExtend5(String extend5) {
		this.extend5 = extend5;
	}

	public String getExtend6() {
		return this.extend6;
	}

	public void setExtend6(String extend6) {
		this.extend6 = extend6;
	}

	public String getExtend7() {
		return this.extend7;
	}

	public void setExtend7(String extend7) {
		this.extend7 = extend7;
	}

	public String getExtend8() {
		return this.extend8;
	}

	public void setExtend8(String extend8) {
		this.extend8 = extend8;
	}

	public String getExtend9() {
		return this.extend9;
	}

	public void setExtend9(String extend9) {
		this.extend9 = extend9;
	}

	public String getExtend10() {
		return this.extend10;
	}

	public void setExtend10(String extend10) {
		this.extend10 = extend10;
	}

	public String getExtend11() {
		return this.extend11;
	}

	public void setExtend11(String extend11) {
		this.extend11 = extend11;
	}

	public String getExtend12() {
		return this.extend12;
	}

	public void setExtend12(String extend12) {
		this.extend12 = extend12;
	}

	public String getExtend13() {
		return this.extend13;
	}

	public void setExtend13(String extend13) {
		this.extend13 = extend13;
	}

	public String getExtend14() {
		return this.extend14;
	}

	public void setExtend14(String extend14) {
		this.extend14 = extend14;
	}

	public String getExtend15() {
		return this.extend15;
	}

	public void setExtend15(String extend15) {
		this.extend15 = extend15;
	}

}
