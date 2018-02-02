package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;

public class OutSourceingApplyVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonSerialize(using = LongSerializer.class)
	private Long outSourceId; // 结算系统申请表ID 便于修改、删除
	
	private String applyDate; // 申请日期
	
	private String applyUser; // 申请人
	
	private String passengerName; // 旅客姓名
	
	private String orderId; // 工单号
	
	private String routing; // 航程
	
	private String flightNo; // 航班
	
	private String departureDate; // 乘机日期
	
	private String pnr; // 编码
	
	private double salePrice; // 销售价（实收金额）
	
	private double purchasePrice; // 买入价（净价合计+税金）
	
	private double profit; // 利润（营业部毛利）
	
	private String accountName; // 收款单位的开户名称
	
	private String accountNo; // 账号
	
	private String openBank; // 开户银行

	private String supplierNo;//供应商编号
	
	private String remarke; // 备注
	
	private String status; // 状态 0:待审核 ; 1:待处理 ; 2:已审核 ; 3:已处理 ; 4:已返回
	
	private String gdId; // 单系统申请表ID,便于同步状态
	
	private String customername; // 客户名称
	
	private String saledep; // 销售部门
	
	private String businessline; // 业务线
	
	private String tanagementarea; // 管理区域
	
	private String ticketno; // 票号
	
	private String platformOrderid; // 平台订单号
	
	private String deptApproval; // 部门批准人
	
	private String filepath; // 上传的水单附件存储地址

	private String backInfo;// 结算处理结果(用于结算通知工单)
	
	private Integer[] refundStatus;

	private String beforeDate;

	private String endDate;

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public Long getOutSourceId() {
		return outSourceId;
	}

	public void setOutSourceId(Long outSourceId) {
		this.outSourceId = outSourceId;
	}

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public String getApplyUser() {
		return applyUser;
	}

	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getRouting() {
		return routing;
	}

	public void setRouting(String routing) {
		this.routing = routing;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}

	public double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public double getProfit() {
		return profit;
	}

	public void setProfit(double profit) {
		this.profit = profit;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getOpenBank() {
		return openBank;
	}

	public void setOpenBank(String openBank) {
		this.openBank = openBank;
	}

	public String getRemarke() {
		return remarke;
	}

	public void setRemarke(String remarke) {
		this.remarke = remarke;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGdId() {
		return gdId;
	}

	public void setGdId(String gdId) {
		this.gdId = gdId;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public String getSaledep() {
		return saledep;
	}

	public void setSaledep(String saledep) {
		this.saledep = saledep;
	}

	public String getBusinessline() {
		return businessline;
	}

	public void setBusinessline(String businessline) {
		this.businessline = businessline;
	}

	public String getTanagementarea() {
		return tanagementarea;
	}

	public void setTanagementarea(String tanagementarea) {
		this.tanagementarea = tanagementarea;
	}

	public String getTicketno() {
		return ticketno;
	}

	public void setTicketno(String ticketno) {
		this.ticketno = ticketno;
	}

	public String getPlatformOrderid() {
		return platformOrderid;
	}

	public void setPlatformOrderid(String platformOrderid) {
		this.platformOrderid = platformOrderid;
	}

	public String getDeptApproval() {
		return deptApproval;
	}

	public void setDeptApproval(String deptApproval) {
		this.deptApproval = deptApproval;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getBackInfo() {
		return backInfo;
	}

	public void setBackInfo(String backInfo) {
		this.backInfo = backInfo;
	}

	public Integer[] getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(Integer[] refundStatus) {
		this.refundStatus = refundStatus;
	}

	public String getBeforeDate() {
		return beforeDate;
	}

	public void setBeforeDate(String beforeDate) {
		this.beforeDate = beforeDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
