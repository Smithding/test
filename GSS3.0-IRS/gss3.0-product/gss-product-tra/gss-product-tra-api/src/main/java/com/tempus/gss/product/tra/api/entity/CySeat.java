package com.tempus.gss.product.tra.api.entity;

import java.io.Serializable;

public class CySeat  implements Serializable {
	private String seatName;//座位名字

	private float price;//价格

	private int seatState;//状态

	private String seats;//座位数量
	
	private String seatType;//类型
	
	private String seatPrice;//价格

	private String ticketCount;
	
	private String bookState;
	
	
	
	public String getSeatType() {
		return seatType;
	}

	public void setSeatType(String seatType) {
		this.seatType = seatType;
	}

	public String getSeatPrice() {
		return seatPrice;
	}

	public void setSeatPrice(String seatPrice) {
		this.seatPrice = seatPrice;
	}

	public String getTicketCount() {
		return ticketCount;
	}

	public void setTicketCount(String ticketCount) {
		this.ticketCount = ticketCount;
	}

	public String getBookState() {
		return bookState;
	}

	public void setBookState(String bookState) {
		this.bookState = bookState;
	}

	public void setSeatName(String seatName) {
		this.seatName = seatName;
	}

	public String getSeatName() {
		return this.seatName;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getPrice() {
		return this.price;
	}

	public void setSeatState(int seatState) {
		this.seatState = seatState;
	}

	public int getSeatState() {
		return this.seatState;
	}

	public void setSeats(String seats) {
		this.seats = seats;
	}

	public String getSeats() {
		return this.seats;
	}
}
