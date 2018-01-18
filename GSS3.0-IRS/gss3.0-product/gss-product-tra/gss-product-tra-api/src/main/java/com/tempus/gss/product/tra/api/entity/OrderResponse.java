package com.tempus.gss.product.tra.api.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

@XStreamAlias("OrderResponse")
public class OrderResponse  implements Serializable {

	@XStreamAlias("ServiceName")
	protected String serviceName;
	@XStreamAlias("Status")
	protected String status;
	@XStreamAlias("Channel")
	protected String channel;
	@XStreamAlias("OrderNumber")
	protected String orderNumber;
	@XStreamAlias("Discription")
	protected String discription;
	@XStreamAlias("PeopleState")
	private String peopleState;
	
	

	public String getPeopleState() {
		return peopleState;
	}

	public void setPeopleState(String peopleState) {
		this.peopleState = peopleState;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String value) {
		this.serviceName = value;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String value) {
		this.status = value;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String value) {
		this.channel = value;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String value) {
		this.orderNumber = value;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String value) {
		this.discription = value;
	}

}
