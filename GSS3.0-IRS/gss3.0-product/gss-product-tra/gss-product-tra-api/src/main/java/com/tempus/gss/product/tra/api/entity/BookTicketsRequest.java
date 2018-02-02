package com.tempus.gss.product.tra.api.entity;

import java.io.Serializable;
import java.util.List;

public class BookTicketsRequest  implements Serializable {
	private String queryKey;// 请求key（必填）

	private String journeyType; //1单程 2往返 （必填，暂时只支持1）

	private String userAccount; //用户账号 密文（可不填）

	private String outOrderNo;//外部订单号（必填）

	private String trainNo;//车次号（必填）

	private String fromStation;//出发车站中文或拼音（必填）

	private String toStation;//到达车站中文或拼音，（必填）

	private int isPost;//是否需要邮寄（0：不需要，1：需要）（必填，isOnLine为0时，isPost必须为1）

	private int isOnLine;//是否线上出票（0：否，1：是），（必填）

	private int noticeType;//占座和出票通知是否合并（0：占座出票不合并，1：合并占座出票）（必填）

	private String batchNumber;//批次号（线下出票时用于合并邮寄）（可不填）

	private String isProduction;//分销占座信息推送地址环境设置  0：测试环境  1：生产环境 无特殊需求请传1（必填）

	private String ticketModel;//购票模式 0：代购模式  1：自购模式  不传默认0（必填）

	private String accountNo;//12306账号(密文)，（ticketModel为1时必填）

	private String accountPwd;//12306密码(密文)，（ticketModel为1时必填）

	private String acceptNoSeat;//是否接受无座，0：不接受  1：接受，（必填）

	private ContactInfo contactInfo;

	private PostalInfo postalInfo;

	private List<Passenger> passengers;

	public void setQueryKey(String queryKey) {
		this.queryKey = queryKey;
	}

	public String getQueryKey() {
		return this.queryKey;
	}

	public void setJourneyType(String journeyType) {
		this.journeyType = journeyType;
	}

	public String getJourneyType() {
		return this.journeyType;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserAccount() {
		return this.userAccount;
	}

	public void setOutOrderNo(String outOrderNo) {
		this.outOrderNo = outOrderNo;
	}

	public String getOutOrderNo() {
		return this.outOrderNo;
	}

	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}

	public String getTrainNo() {
		return this.trainNo;
	}

	public void setFromStation(String fromStation) {
		this.fromStation = fromStation;
	}

	public String getFromStation() {
		return this.fromStation;
	}

	public void setToStation(String toStation) {
		this.toStation = toStation;
	}

	public String getToStation() {
		return this.toStation;
	}

	public void setIsPost(int isPost) {
		this.isPost = isPost;
	}

	public int getIsPost() {
		return this.isPost;
	}

	public void setIsOnLine(int isOnLine) {
		this.isOnLine = isOnLine;
	}

	public int getIsOnLine() {
		return this.isOnLine;
	}

	public void setNoticeType(int noticeType) {
		this.noticeType = noticeType;
	}

	public int getNoticeType() {
		return this.noticeType;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public String getBatchNumber() {
		return this.batchNumber;
	}

	public void setIsProduction(String isProduction) {
		this.isProduction = isProduction;
	}

	public String getIsProduction() {
		return this.isProduction;
	}

	public void setTicketModel(String ticketModel) {
		this.ticketModel = ticketModel;
	}

	public String getTicketModel() {
		return this.ticketModel;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountNo() {
		return this.accountNo;
	}

	public void setAccountPwd(String accountPwd) {
		this.accountPwd = accountPwd;
	}

	public String getAccountPwd() {
		return this.accountPwd;
	}

	public void setAcceptNoSeat(String acceptNoSeat) {
		this.acceptNoSeat = acceptNoSeat;
	}

	public String getAcceptNoSeat() {
		return this.acceptNoSeat;
	}

	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}

	public ContactInfo getContactInfo() {
		return this.contactInfo;
	}

	public void setPostalInfo(PostalInfo postalInfo) {
		this.postalInfo = postalInfo;
	}

	public PostalInfo getPostalInfo() {
		return this.postalInfo;
	}

	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}

	public List<Passenger> getPassengers() {
		return this.passengers;
	}
}
