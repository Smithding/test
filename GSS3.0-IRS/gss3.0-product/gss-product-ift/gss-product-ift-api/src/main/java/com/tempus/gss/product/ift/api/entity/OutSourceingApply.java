package com.tempus.gss.product.ift.api.entity;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 *
 * 国际外购付款申请表
 *
 */
public class OutSourceingApply implements Serializable {

	private static final long serialVersionUID = 1L;

	/**  */
	private Long id;

	/** 申请日期 */
	private String applyDate;

	/**  */
	private String applyUser;

	/** 旅客姓名 */
	private String passengerName;

	/** 工单号 */
	private String orderid;

	/** 航程 */
	private String routing;

	/*供应商编号*/
	private String supplierNo;

	/*供应商名称*/
	private String supplierName;
	/** 航班 */
	private String flightNo;

	/** 乘机日期 */
	private Date departureDate;

	/** 编码 */
	private String pnr;

	/** 销售价（实收金额） */
	private BigDecimal salePrice;

	/** 买入价（净价合计+税金） */
	private BigDecimal purchasePrice;

	/** 利润（营业部毛利） */
	private BigDecimal profit;

	/** 收款单位的开户名称 */
	private String accountName;

	/** 账号 */
	private String accountNo;

	/** 开户银行 */
	private String openBank;

	/** 备注 */
	private String remarke;

	/** 状态 0:待审批 ; 1:已审批 ; 2:已处理 */
	private String status;

	/** 客户名称 */
	private String customername;

	/** 销售部门 */
	private String saledep;

	/** 业务线 */
	private String businessline;

	/** 管理区域 */
	private String tanagementarea;

	/** 票号 */
	private String ticketno;

	/** 平台订单号 */
	private String platformOrderid;

	/** 部门批准人 */
	private String deptApproval;

	/** 上传的水单附件存储地址 */
	private String filepath;

	/** 结算系统申请表ID 便于修改、删除 */
	private Long outSourceId;

	/** 结算返回信息 */
	private String backInfo;


	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getApplyDate() {
		return this.applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public String getApplyUser() {
		return this.applyUser;
	}

	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}

	public String getPassengerName() {
		return this.passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public String getOrderid() {
		return this.orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getRouting() {
		return this.routing;
	}

	public void setRouting(String routing) {
		this.routing = routing;
	}

	public String getFlightNo() {
		return this.flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public Date getDepartureDate() {
		return this.departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public String getPnr() {
		return this.pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public BigDecimal getSalePrice() {
		return this.salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public BigDecimal getPurchasePrice() {
		return this.purchasePrice;
	}

	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public BigDecimal getProfit() {
		return this.profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}

	public String getAccountName() {
		return this.accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNo() {
		return this.accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getOpenBank() {
		return this.openBank;
	}

	public void setOpenBank(String openBank) {
		this.openBank = openBank;
	}

	public String getRemarke() {
		return this.remarke;
	}

	public void setRemarke(String remarke) {
		this.remarke = remarke;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCustomername() {
		return this.customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public String getSaledep() {
		return this.saledep;
	}

	public void setSaledep(String saledep) {
		this.saledep = saledep;
	}

	public String getBusinessline() {
		return this.businessline;
	}

	public void setBusinessline(String businessline) {
		this.businessline = businessline;
	}

	public String getTanagementarea() {
		return this.tanagementarea;
	}

	public void setTanagementarea(String tanagementarea) {
		this.tanagementarea = tanagementarea;
	}

	public String getTicketno() {
		return this.ticketno;
	}

	public void setTicketno(String ticketno) {
		this.ticketno = ticketno;
	}

	public String getPlatformOrderid() {
		return this.platformOrderid;
	}

	public void setPlatformOrderid(String platformOrderid) {
		this.platformOrderid = platformOrderid;
	}

	public String getDeptApproval() {
		return this.deptApproval;
	}

	public void setDeptApproval(String deptApproval) {
		this.deptApproval = deptApproval;
	}

	public String getFilepath() {
		return this.filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public Long getOutSourceId() {
		return this.outSourceId;
	}

	public void setOutSourceId(Long outSourceId) {
		this.outSourceId = outSourceId;
	}

	public String getBackInfo() {
		return this.backInfo;
	}

	public void setBackInfo(String backInfo) {
		this.backInfo = backInfo;
	}

}
