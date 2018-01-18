package com.tempus.gss.product.ift.api.entity;


public class Legpassenger {
 
	private Leg leg;
	
	private Passenger passenger;
	
	private String ticketNo;

	public Passenger getPassenger() {
		return passenger;
	}

	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}

	public Leg getLeg() {
		return leg;
	}

	public void setLeg(Leg leg) {
		this.leg = leg;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	@Override
	public String toString() {
		return "Legpassenger [leg=" + leg + ", passenger=" + passenger + ", ticketNo=" + ticketNo + "]";
	}
	
	
}
