/**
 * PlaneTicket.java
 * com.tempus.gss.sbs.entity
 *
 * Function： 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2017-3-31 		lian.chen
 *
 * Copyright (c) 2017, TNT All Rights Reserved.
*/
package com.tempus.gss.product.ift.api.entity;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 机票产品表.
 *
 * @author   lian.chen
 * @version 
 * @see 
 * @since    Ver 1.1
 * @Date  2017-3-31		17:44:52
 */
@TableName("SBS_PLANE_TICKET")
public class IftPlaneTicket implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** 主键 */
	@TableId(type = IdType.AUTO)
	private Long  id;

	/** 数据归属单位 */
	private Integer owner;//

	/** 销售公司 */
	private String saleCom;//

	/** 结算公司 */
	private String accCom;//

	/** 产品类型 */
	private String proType;//

	/** 业务线 */
	private String line;//

	/** 销售部门 */
	private String saleDept;//

	/** 客户类型 */
	private String cusType;//

	/** 客户名称 */
	private String cusName;//

	/** 客户编码 */
	private String cusCode;//

	/** 销售订单号 */
	private String saleOrdNum;//

	/** 产品订单号 */
	private String proOrdNum;

	/** 帐单号 */
	private String billNum;//

	/** 销售订单来源 */
	private String saleSource;

	/** 交易日期 */
	private String issueDate;//

	/** 航班日期 */
	private String flightDate;

	/** 退票日期 */
	private String refundDate;

	/** 承运人 */
	private String airCom;

	/** 航空结算码 */
	private String airCode;

	/** 票号 */
	private String ticketNo;

	/** 航程 */
	private String routing;

	/** 航班 */
	private String flight;

	/** 舱位 */
	private String cabin;

	/** 旅客姓名 */
	private String passName;

	/** PNR */
	private String pnr;

	/** 票价 */
	private BigDecimal ticPrice;

	/** 税费 */
	private BigDecimal tax;

	/** 佣金 */
	private BigDecimal commission;

	/** 服务费 */
	private BigDecimal serCharge;

	/** 退票手续费 */
	private BigDecimal refundFee;

	/** 应收金额 */
	private BigDecimal receAmount;//

	/** 应退金额 */
	private BigDecimal refundAmount;

	/** 应付供应商手续费 */
	private BigDecimal paySuppFee;

	/** 应付金额 */
	private BigDecimal payAmount;

	/** 实退金额 */
	private BigDecimal realAmount;

	/** 实付金额 */
	private BigDecimal paidAmount;//

	/** 供应商支付方式 */
	private String suppPayMode;

	/** 供应商支付流水 */
	private String suppPayNum;

	/** OFFICE */
	private String office;

	/** 供应商名称 */
	private String supplierName;//

	/** 行程单号 */
	private String tripNum;

	/** 客服人员 */
	private String cusPeo;

	/** 出票人员 */
	private String ticPeo;

	/** 出票部门 */
	private String ticketDep;

	/** 客票状态 出票
	改签
	调账
	废票
	*/
	private String ticStatus;

	/** 毛利 */
	private BigDecimal profit;

	/** 汇率 */
	private BigDecimal rate;

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

	/** 删除标志 0 无效 已删除 1 有效 */
	private Boolean valid;

	/** 上传错误原因说明 */
	private String errorCause;

	/** 上传时间 */
	private Date uploadTime;

	/** 机票类别 0采购出票， 1采购退票，2销售出票，3销售退票 */
	private String extend1;

	/** 扩展字段2  供应商后返点数*/
	private String extend2;

	/** 扩展字段3 供应商后返金额 */
	private String extend3;

	/** 扩展字段4 渠道底价金额 */
	private String extend4;

	/** 扩展字段5 供应商后返金额 */
	private String extend5;

	/** 扩展字段6 销售毛利 */
	private String extend6;

	/** 扩展字段7 币种 */
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

	/** 交易单号 **/
	private String tradeNo;
	/**
	 * 获取主键
	 *
	 * @return id 主键
	*/
	public Long getId() {

		return id;
	}

	/**
	 * 设置主键
	 *
	 * @param id 主键
	 */
	public void setId(Long id) {

		this.id = id;
	}

	/**
	 * 获取数据归属单位
	 *
	 * @return owner 数据归属单位
	*/
	public Integer getOwner() {

		return owner;
	}

	/**
	 * 设置数据归属单位
	 *
	 * @param owner 数据归属单位
	 */
	public void setOwner(Integer owner) {

		this.owner = owner;
	}

	/**
	 * 获取销售公司
	 *
	 * @return saleCom 销售公司
	*/
	public String getSaleCom() {

		return saleCom;
	}

	/**
	 * 设置销售公司
	 *
	 * @param saleCom 销售公司
	 */
	public void setSaleCom(String saleCom) {

		this.saleCom = saleCom;
	}

	/**
	 * 获取结算公司
	 *
	 * @return accCom 结算公司
	*/
	public String getAccCom() {

		return accCom;
	}

	/**
	 * 设置结算公司
	 *
	 * @param accCom 结算公司
	 */
	public void setAccCom(String accCom) {

		this.accCom = accCom;
	}

	/**
	 * 获取产品类型
	 *
	 * @return proType 产品类型
	*/
	public String getProType() {

		return proType;
	}

	/**
	 * 设置产品类型
	 *
	 * @param proType 产品类型
	 */
	public void setProType(String proType) {

		this.proType = proType;
	}

	/**
	 * 获取业务线
	 *
	 * @return line 业务线
	*/
	public String getLine() {

		return line;
	}

	/**
	 * 设置业务线
	 *
	 * @param line 业务线
	 */
	public void setLine(String line) {

		this.line = line;
	}

	/**
	 * 获取销售部门
	 *
	 * @return saleDept 销售部门
	*/
	public String getSaleDept() {

		return saleDept;
	}

	/**
	 * 设置销售部门
	 *
	 * @param saleDept 销售部门
	 */
	public void setSaleDept(String saleDept) {

		this.saleDept = saleDept;
	}

	/**
	 * 获取客户类型
	 *
	 * @return cusType 客户类型
	*/
	public String getCusType() {

		return cusType;
	}

	/**
	 * 设置客户类型
	 *
	 * @param cusType 客户类型
	 */
	public void setCusType(String cusType) {

		this.cusType = cusType;
	}

	/**
	 * 获取客户名称
	 *
	 * @return cusName 客户名称
	*/
	public String getCusName() {

		return cusName;
	}

	/**
	 * 设置客户名称
	 *
	 * @param cusName 客户名称
	 */
	public void setCusName(String cusName) {

		this.cusName = cusName;
	}

	/**
	 * 获取客户编码
	 *
	 * @return cusCode 客户编码
	*/
	public String getCusCode() {

		return cusCode;
	}

	/**
	 * 设置客户编码
	 *
	 * @param cusCode 客户编码
	 */
	public void setCusCode(String cusCode) {

		this.cusCode = cusCode;
	}

	/**
	 * 获取销售订单号
	 *
	 * @return saleOrdNum 销售订单号
	*/
	public String getSaleOrdNum() {

		return saleOrdNum;
	}

	/**
	 * 设置销售订单号
	 *
	 * @param saleOrdNum 销售订单号
	 */
	public void setSaleOrdNum(String saleOrdNum) {

		this.saleOrdNum = saleOrdNum;
	}

	/**
	 * 获取产品订单号
	 *
	 * @return proOrdNum 产品订单号
	*/
	public String getProOrdNum() {

		return proOrdNum;
	}

	/**
	 * 设置产品订单号
	 *
	 * @param proOrdNum 产品订单号
	 */
	public void setProOrdNum(String proOrdNum) {

		this.proOrdNum = proOrdNum;
	}

	/**
	 * 获取帐单号
	 *
	 * @return billNum 帐单号
	*/
	public String getBillNum() {

		return billNum;
	}

	/**
	 * 设置帐单号
	 *
	 * @param billNum 帐单号
	 */
	public void setBillNum(String billNum) {

		this.billNum = billNum;
	}

	/**
	 * 获取销售订单来源
	 *
	 * @return saleSource 销售订单来源
	*/
	public String getSaleSource() {

		return saleSource;
	}

	/**
	 * 设置销售订单来源
	 *
	 * @param saleSource 销售订单来源
	 */
	public void setSaleSource(String saleSource) {

		this.saleSource = saleSource;
	}

	/**
	 * 获取交易日期
	 *
	 * @return issueDate 交易日期
	*/
	public String getIssueDate() {

		return issueDate;
	}

	/**
	 * 设置交易日期
	 *
	 * @param issueDate 交易日期
	 */
	public void setIssueDate(String issueDate) {

		this.issueDate = issueDate;
	}

	/**
	 * 获取航班日期
	 *
	 * @return flightDate 航班日期
	*/
	public String getFlightDate() {

		return flightDate;
	}

	/**
	 * 设置航班日期
	 *
	 * @param flightDate 航班日期
	 */
	public void setFlightDate(String flightDate) {

		this.flightDate = flightDate;
	}

	/**
	 * 获取退票日期
	 *
	 * @return refundDate 退票日期
	*/
	public String getRefundDate() {

		return refundDate;
	}

	/**
	 * 设置退票日期
	 *
	 * @param refundDate 退票日期
	 */
	public void setRefundDate(String refundDate) {

		this.refundDate = refundDate;
	}

	/**
	 * 获取承运人
	 *
	 * @return airCom 承运人
	*/
	public String getAirCom() {

		return airCom;
	}

	/**
	 * 设置承运人
	 *
	 * @param airCom 承运人
	 */
	public void setAirCom(String airCom) {

		this.airCom = airCom;
	}

	/**
	 * 获取航空结算码
	 *
	 * @return airCode 航空结算码
	*/
	public String getAirCode() {

		return airCode;
	}

	/**
	 * 设置航空结算码
	 *
	 * @param airCode 航空结算码
	 */
	public void setAirCode(String airCode) {

		this.airCode = airCode;
	}

	/**
	 * 获取票号
	 *
	 * @return ticketNo 票号
	*/
	public String getTicketNo() {

		return ticketNo;
	}

	/**
	 * 设置票号
	 *
	 * @param ticketNo 票号
	 */
	public void setTicketNo(String ticketNo) {

		this.ticketNo = ticketNo;
	}

	/**
	 * 获取航程
	 *
	 * @return routing 航程
	*/
	public String getRouting() {

		return routing;
	}

	/**
	 * 设置航程
	 *
	 * @param routing 航程
	 */
	public void setRouting(String routing) {

		this.routing = routing;
	}

	/**
	 * 获取航班
	 *
	 * @return flight 航班
	*/
	public String getFlight() {

		return flight;
	}

	/**
	 * 设置航班
	 *
	 * @param flight 航班
	 */
	public void setFlight(String flight) {

		this.flight = flight;
	}

	/**
	 * 获取舱位
	 *
	 * @return cabin 舱位
	*/
	public String getCabin() {

		return cabin;
	}

	/**
	 * 设置舱位
	 *
	 * @param cabin 舱位
	 */
	public void setCabin(String cabin) {

		this.cabin = cabin;
	}

	/**
	 * 获取旅客姓名
	 *
	 * @return passName 旅客姓名
	*/
	public String getPassName() {

		return passName;
	}

	/**
	 * 设置旅客姓名
	 *
	 * @param passName 旅客姓名
	 */
	public void setPassName(String passName) {

		this.passName = passName;
	}

	/**
	 * 获取PNR
	 *
	 * @return pnr PNR
	*/
	public String getPnr() {

		return pnr;
	}

	/**
	 * 设置PNR
	 *
	 * @param pnr PNR
	 */
	public void setPnr(String pnr) {

		this.pnr = pnr;
	}

	/**
	 * 获取票价
	 *
	 * @return ticPrice 票价
	*/
	public BigDecimal getTicPrice() {

		return ticPrice;
	}

	/**
	 * 设置票价
	 *
	 * @param ticPrice 票价
	 */
	public void setTicPrice(BigDecimal ticPrice) {

		this.ticPrice = ticPrice;
	}

	/**
	 * 获取税费
	 *
	 * @return tax 税费
	*/
	public BigDecimal getTax() {

		return tax;
	}

	/**
	 * 设置税费
	 *
	 * @param tax 税费
	 */
	public void setTax(BigDecimal tax) {

		this.tax = tax;
	}

	/**
	 * 获取佣金
	 *
	 * @return commission 佣金
	*/
	public BigDecimal getCommission() {

		return commission;
	}

	/**
	 * 设置佣金
	 *
	 * @param commission 佣金
	 */
	public void setCommission(BigDecimal commission) {

		this.commission = commission;
	}

	/**
	 * 获取服务费
	 *
	 * @return serCharge 服务费
	*/
	public BigDecimal getSerCharge() {

		return serCharge;
	}

	/**
	 * 设置服务费
	 *
	 * @param serCharge 服务费
	 */
	public void setSerCharge(BigDecimal serCharge) {

		this.serCharge = serCharge;
	}

	/**
	 * 获取退票手续费
	 *
	 * @return refundFee 退票手续费
	*/
	public BigDecimal getRefundFee() {

		return refundFee;
	}

	public String getTicketDep() {
		return ticketDep;
	}

	public void setTicketDep(String ticketDep) {
		this.ticketDep = ticketDep;
	}

	/**
	 * 设置退票手续费
	 *
	 * @param refundFee 退票手续费
	 */
	public void setRefundFee(BigDecimal refundFee) {

		this.refundFee = refundFee;
	}

	/**
	 * 获取应收金额
	 *
	 * @return receAmount 应收金额
	*/
	public BigDecimal getReceAmount() {

		return receAmount;
	}

	/**
	 * 设置应收金额
	 *
	 * @param receAmount 应收金额
	 */
	public void setReceAmount(BigDecimal receAmount) {

		this.receAmount = receAmount;
	}

	/**
	 * 获取应退金额
	 *
	 * @return refundAmount 应退金额
	*/
	public BigDecimal getRefundAmount() {

		return refundAmount;
	}

	/**
	 * 设置应退金额
	 *
	 * @param refundAmount 应退金额
	 */
	public void setRefundAmount(BigDecimal refundAmount) {

		this.refundAmount = refundAmount;
	}

	/**
	 * 获取应付供应商手续费
	 *
	 * @return paySuppFee 应付供应商手续费
	*/
	public BigDecimal getPaySuppFee() {

		return paySuppFee;
	}

	/**
	 * 设置应付供应商手续费
	 *
	 * @param paySuppFee 应付供应商手续费
	 */
	public void setPaySuppFee(BigDecimal paySuppFee) {

		this.paySuppFee = paySuppFee;
	}

	/**
	 * 获取应付金额
	 *
	 * @return payAmount 应付金额
	*/
	public BigDecimal getPayAmount() {

		return payAmount;
	}

	/**
	 * 设置应付金额
	 *
	 * @param payAmount 应付金额
	 */
	public void setPayAmount(BigDecimal payAmount) {

		this.payAmount = payAmount;
	}

	/**
	 * 获取实退金额
	 *
	 * @return realAmount 实退金额
	*/
	public BigDecimal getRealAmount() {

		return realAmount;
	}

	/**
	 * 设置实退金额
	 *
	 * @param realAmount 实退金额
	 */
	public void setRealAmount(BigDecimal realAmount) {

		this.realAmount = realAmount;
	}

	/**
	 * 获取实付金额
	 *
	 * @return paidAmount 实付金额
	*/
	public BigDecimal getPaidAmount() {

		return paidAmount;
	}

	/**
	 * 设置实付金额
	 *
	 * @param paidAmount 实付金额
	 */
	public void setPaidAmount(BigDecimal paidAmount) {

		this.paidAmount = paidAmount;
	}

	/**
	 * 获取供应商支付方式
	 *
	 * @return suppPayMode 供应商支付方式
	*/
	public String getSuppPayMode() {

		return suppPayMode;
	}

	/**
	 * 设置供应商支付方式
	 *
	 * @param suppPayMode 供应商支付方式
	 */
	public void setSuppPayMode(String suppPayMode) {

		this.suppPayMode = suppPayMode;
	}

	/**
	 * 获取供应商支付流水
	 *
	 * @return suppPayNum 供应商支付流水
	*/
	public String getSuppPayNum() {

		return suppPayNum;
	}

	/**
	 * 设置供应商支付流水
	 *
	 * @param suppPayNum 供应商支付流水
	 */
	public void setSuppPayNum(String suppPayNum) {

		this.suppPayNum = suppPayNum;
	}

	/**
	 * 获取OFFICE
	 *
	 * @return office OFFICE
	*/
	public String getOffice() {

		return office;
	}

	/**
	 * 设置OFFICE
	 *
	 * @param office OFFICE
	 */
	public void setOffice(String office) {

		this.office = office;
	}

	/**
	 * 获取供应商名称
	 *
	 * @return supplierName 供应商名称
	*/
	public String getSupplierName() {

		return supplierName;
	}

	/**
	 * 设置供应商名称
	 *
	 * @param supplierName 供应商名称
	 */
	public void setSupplierName(String supplierName) {

		this.supplierName = supplierName;
	}

	/**
	 * 获取行程单号
	 *
	 * @return tripNum 行程单号
	*/
	public String getTripNum() {

		return tripNum;
	}

	/**
	 * 设置行程单号
	 *
	 * @param tripNum 行程单号
	 */
	public void setTripNum(String tripNum) {

		this.tripNum = tripNum;
	}

	/**
	 * 获取客服人员
	 *
	 * @return cusPeo 客服人员
	*/
	public String getCusPeo() {

		return cusPeo;
	}

	/**
	 * 设置客服人员
	 *
	 * @param cusPeo 客服人员
	 */
	public void setCusPeo(String cusPeo) {

		this.cusPeo = cusPeo;
	}

	/**
	 * 获取出票人员
	 *
	 * @return ticPeo 出票人员
	*/
	public String getTicPeo() {

		return ticPeo;
	}

	/**
	 * 设置出票人员
	 *
	 * @param ticPeo 出票人员
	 */
	public void setTicPeo(String ticPeo) {

		this.ticPeo = ticPeo;
	}

	/**
	 * 获取客票状态出票改签调账废票
	 *
	 * @return ticStatus 客票状态出票改签调账废票
	*/
	public String getTicStatus() {

		return ticStatus;
	}

	/**
	 * 设置客票状态出票改签调账废票
	 *
	 * @param ticStatus 客票状态出票改签调账废票
	 */
	public void setTicStatus(String ticStatus) {

		this.ticStatus = ticStatus;
	}

	/**
	 * 获取毛利
	 *
	 * @return profit 毛利
	*/
	public BigDecimal getProfit() {

		return profit;
	}

	/**
	 * 设置毛利
	 *
	 * @param profit 毛利
	 */
	public void setProfit(BigDecimal profit) {

		this.profit = profit;
	}

	/**
	 * 获取汇率
	 *
	 * @return rate 汇率
	*/
	public BigDecimal getRate() {

		return rate;
	}

	/**
	 * 设置汇率
	 *
	 * @param rate 汇率
	 */
	public void setRate(BigDecimal rate) {

		this.rate = rate;
	}

	/**
	 * 获取备注
	 *
	 * @return remarks 备注
	*/
	public String getRemarks() {

		return remarks;
	}

	/**
	 * 设置备注
	 *
	 * @param remarks 备注
	 */
	public void setRemarks(String remarks) {

		this.remarks = remarks;
	}

	/**
	 * 获取创建时间
	 *
	 * @return createTime 创建时间
	*/
	public Date getCreateTime() {

		return createTime;
	}

	/**
	 * 设置创建时间
	 *
	 * @param createTime 创建时间
	 */
	public void setCreateTime(Date createTime) {

		this.createTime = createTime;
	}

	/**
	 * 获取修改时间
	 *
	 * @return modifyTime 修改时间
	*/
	public Date getModifyTime() {

		return modifyTime;
	}

	/**
	 * 设置修改时间
	 *
	 * @param modifyTime 修改时间
	 */
	public void setModifyTime(Date modifyTime) {

		this.modifyTime = modifyTime;
	}

	/**
	 * 获取操作人
	 *
	 * @return modifier 操作人
	*/
	public String getModifier() {

		return modifier;
	}

	/**
	 * 设置操作人
	 *
	 * @param modifier 操作人
	 */
	public void setModifier(String modifier) {

		this.modifier = modifier;
	}

	/**
	 * 获取上传状态
	 *
	 * @return uploadStatus 上传状态
	*/
	public String getUploadStatus() {

		return uploadStatus;
	}

	/**
	 * 设置上传状态
	 *
	 * @param uploadStatus 上传状态
	 */
	public void setUploadStatus(String uploadStatus) {

		this.uploadStatus = uploadStatus;
	}

	/**
	 * 获取删除标志0无效已删除1有效
	 *
	 * @return valid 删除标志0无效已删除1有效
	*/
	public Boolean getValid() {

		return valid;
	}

	/**
	 * 设置删除标志0无效已删除1有效
	 *
	 * @param valid 删除标志0无效已删除1有效
	 */
	public void setValid(Boolean valid) {

		this.valid = valid;
	}

	/**
	 * 获取上传错误原因说明
	 *
	 * @return errorCause 上传错误原因说明
	*/
	public String getErrorCause() {

		return errorCause;
	}

	/**
	 * 设置上传错误原因说明
	 *
	 * @param errorCause 上传错误原因说明
	 */
	public void setErrorCause(String errorCause) {

		this.errorCause = errorCause;
	}

	/**
	 * 获取上传时间
	 *
	 * @return uploadTime 上传时间
	*/
	public Date getUploadTime() {

		return uploadTime;
	}

	/**
	 * 设置上传时间
	 *
	 * @param uploadTime 上传时间
	 */
	public void setUploadTime(Date uploadTime) {

		this.uploadTime = uploadTime;
	}

	/**
	 * 获取机票类别0采购出票，1采购退票，2销售出票，3销售退票
	 *
	 * @return extend1 机票类别0采购出票，1采购退票，2销售出票，3销售退票
	*/
	public String getExtend1() {

		return extend1;
	}

	/**
	 * 设置机票类别0采购出票，1采购退票，2销售出票，3销售退票
	 *
	 * @param extend1 机票类别0采购出票，1采购退票，2销售出票，3销售退票
	 */
	public void setExtend1(String extend1) {

		this.extend1 = extend1;
	}

	/**
	 * 获取扩展字段2
	 *
	 * @return extend2 扩展字段2
	*/
	public String getExtend2() {

		return extend2;
	}

	/**
	 * 设置扩展字段2
	 *
	 * @param extend2 扩展字段2
	 */
	public void setExtend2(String extend2) {

		this.extend2 = extend2;
	}

	/**
	 * 获取扩展字段3采购支付账户
	 *
	 * @return extend3 扩展字段3采购支付账户
	*/
	public String getExtend3() {

		return extend3;
	}

	/**
	 * 设置扩展字段3采购支付账户
	 *
	 * @param extend3 扩展字段3采购支付账户
	 */
	public void setExtend3(String extend3) {

		this.extend3 = extend3;
	}

	/**
	 * 获取扩展字段4销售底价
	 *
	 * @return extend4 扩展字段4销售底价
	*/
	public String getExtend4() {

		return extend4;
	}

	/**
	 * 设置扩展字段4销售底价
	 *
	 * @param extend4 扩展字段4销售底价
	 */
	public void setExtend4(String extend4) {

		this.extend4 = extend4;
	}

	/**
	 * 获取扩展字段5产品毛利
	 *
	 * @return extend5 扩展字段5产品毛利
	*/
	public String getExtend5() {

		return extend5;
	}

	/**
	 * 设置扩展字段5产品毛利
	 *
	 * @param extend5 扩展字段5产品毛利
	 */
	public void setExtend5(String extend5) {

		this.extend5 = extend5;
	}

	/**
	 * 获取扩展字段6销售毛利
	 *
	 * @return extend6 扩展字段6销售毛利
	*/
	public String getExtend6() {

		return extend6;
	}

	/**
	 * 设置扩展字段6销售毛利
	 *
	 * @param extend6 扩展字段6销售毛利
	 */
	public void setExtend6(String extend6) {

		this.extend6 = extend6;
	}

	/**
	 * 获取扩展字段7币种
	 *
	 * @return extend7 扩展字段7币种
	*/
	public String getExtend7() {

		return extend7;
	}

	/**
	 * 设置扩展字段7币种
	 *
	 * @param extend7 扩展字段7币种
	 */
	public void setExtend7(String extend7) {

		this.extend7 = extend7;
	}

	/**
	 * 获取扩展字段8
	 *
	 * @return extend8 扩展字段8
	*/
	public String getExtend8() {

		return extend8;
	}

	/**
	 * 设置扩展字段8
	 *
	 * @param extend8 扩展字段8
	 */
	public void setExtend8(String extend8) {

		this.extend8 = extend8;
	}

	/**
	 * 获取扩展字段9
	 *
	 * @return extend9 扩展字段9
	*/
	public String getExtend9() {

		return extend9;
	}

	/**
	 * 设置扩展字段9
	 *
	 * @param extend9 扩展字段9
	 */
	public void setExtend9(String extend9) {

		this.extend9 = extend9;
	}

	/**
	 * 获取扩展字段10
	 *
	 * @return extend10 扩展字段10
	*/
	public String getExtend10() {

		return extend10;
	}

	/**
	 * 设置扩展字段10
	 *
	 * @param extend10 扩展字段10
	 */
	public void setExtend10(String extend10) {

		this.extend10 = extend10;
	}

	/**
	 * 获取扩展字段11
	 *
	 * @return extend11 扩展字段11
	*/
	public String getExtend11() {

		return extend11;
	}

	/**
	 * 设置扩展字段11
	 *
	 * @param extend11 扩展字段11
	 */
	public void setExtend11(String extend11) {

		this.extend11 = extend11;
	}

	/**
	 * 获取扩展字段12
	 *
	 * @return extend12 扩展字段12
	*/
	public String getExtend12() {

		return extend12;
	}

	/**
	 * 设置扩展字段12
	 *
	 * @param extend12 扩展字段12
	 */
	public void setExtend12(String extend12) {

		this.extend12 = extend12;
	}

	/**
	 * 获取扩展字段13
	 *
	 * @return extend13 扩展字段13
	*/
	public String getExtend13() {

		return extend13;
	}

	/**
	 * 设置扩展字段13
	 *
	 * @param extend13 扩展字段13
	 */
	public void setExtend13(String extend13) {

		this.extend13 = extend13;
	}

	/**
	 * 获取扩展字段14
	 *
	 * @return extend14 扩展字段14
	*/
	public String getExtend14() {

		return extend14;
	}

	/**
	 * 设置扩展字段14
	 *
	 * @param extend14 扩展字段14
	 */
	public void setExtend14(String extend14) {

		this.extend14 = extend14;
	}

	/**
	 * 获取扩展字段15
	 *
	 * @return extend15 扩展字段15
	*/
	public String getExtend15() {

		return extend15;
	}

	/**
	 * 设置扩展字段15
	 *
	 * @param extend15 扩展字段15
	 */
	public void setExtend15(String extend15) {

		this.extend15 = extend15;
	}

	
	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	/**
	 * toString:(这里用一句话描述这个方法的作用)
	 *
	 * @return
	*/
	@Override
	public String toString() {

		return "PlaneTicket [id=" + id + ", owner=" + owner + ", saleCom=" + saleCom + ", accCom=" + accCom + ", proType="
				+ proType + ", line=" + line + ", saleDept=" + saleDept + ", cusType=" + cusType + ", cusName=" + cusName
				+ ", cusCode=" + cusCode + ", saleOrdNum=" + saleOrdNum + ", proOrdNum=" + proOrdNum + ", billNum=" + billNum
				+ ", saleSource=" + saleSource + ", issueDate=" + issueDate + ", flightDate=" + flightDate + ", refundDate="
				+ refundDate + ", airCom=" + airCom + ", airCode=" + airCode + ", ticketNo=" + ticketNo + ", routing="
				+ routing + ", flight=" + flight + ", cabin=" + cabin + ", passName=" + passName + ", pnr=" + pnr
				+ ", ticPrice=" + ticPrice + ", tax=" + tax + ", commission=" + commission + ", serCharge=" + serCharge
				+ ", refundFee=" + refundFee + ", receAmount=" + receAmount + ", refundAmount=" + refundAmount
				+ ", paySuppFee=" + paySuppFee + ", payAmount=" + payAmount + ", realAmount=" + realAmount + ", paidAmount="
				+ paidAmount + ", suppPayMode=" + suppPayMode + ", suppPayNum=" + suppPayNum + ", office=" + office
				+ ", supplierName=" + supplierName + ", tripNum=" + tripNum + ", cusPeo=" + cusPeo + ", ticPeo=" + ticPeo
				+ ", ticStatus=" + ticStatus + ", profit=" + profit + ", rate=" + rate + ", remarks=" + remarks
				+ ", createTime=" + createTime + ", modifyTime=" + modifyTime + ", modifier=" + modifier + ", uploadStatus="
				+ uploadStatus + ", valid=" + valid + ", errorCause=" + errorCause + ", uploadTime=" + uploadTime
				+ ", extend1=" + extend1 + ", extend2=" + extend2 + ", extend3=" + extend3 + ", extend4=" + extend4
				+ ", extend5=" + extend5 + ", extend6=" + extend6 + ", extend7=" + extend7 + ", extend8=" + extend8
				+ ", extend9=" + extend9 + ", extend10=" + extend10 + ", extend11=" + extend11 + ", extend12=" + extend12
				+ ", extend13=" + extend13 + ", extend14=" + extend14 + ", extend15=" + extend15 + "]";
	}

}
