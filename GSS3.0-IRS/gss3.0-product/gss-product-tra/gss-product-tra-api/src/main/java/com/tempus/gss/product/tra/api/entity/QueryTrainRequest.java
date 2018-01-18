package com.tempus.gss.product.tra.api.entity;

import java.io.Serializable;
import java.util.List;

public class QueryTrainRequest  implements Serializable {

	private String travelType;//出差类型(1因私;2因公)
	private String memchantID;// 商户ID
	private	String accessToken;//app token
	private String from;//出发城市
	private String to;//到达城市
	private String date;//出行日期(yyyy-MM-dd)
	private List<String> passengerList;//出行人
	private String enterpriseCode;//企业三字码
	private String ticketType;//票种类型 ，为空则默认为1 1：正常票2：学生票
	
	
	public String getTicketType() {
		return ticketType;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTravelType() {
		return travelType;
	}

	public void setTravelType(String travelType) {
		this.travelType = travelType;
	}

	public String getMemchantID() {
		return memchantID;
	}

	public void setMemchantID(String memchantID) {
		this.memchantID = memchantID;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getEnterpriseCode() {
		return enterpriseCode;
	}

	public void setEnterpriseCode(String enterpriseCode) {
		this.enterpriseCode = enterpriseCode;
	}

	public List<String> getPassengerList() {
		return passengerList;
	}

	public void setPassengerList(List<String> passengerList) {
		this.passengerList = passengerList;
	}
	
	
}
