package com.tempus.gss.product.ift.api.entity.vo;


import java.io.Serializable;
import java.util.List;

import com.tempus.gss.product.ift.api.entity.Leg;
import com.tempus.gss.product.ift.api.entity.Passenger;
import com.tempus.gss.product.ift.api.entity.PassengerChangePrice;
import com.tempus.gss.product.ift.api.entity.PassengerRefundPrice;

/**
 * Created by Administrator on 2016/10/20.
 */
/**
 * @author Administrator
 *
 */
public class SaleChangeExtReturn implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Leg> legList;
	
	private List<Passenger> passengerList;
	
	private List<PassengerChangePrice> passengerChangePriceList;
	
	private List<PassengerRefundPrice> passengerRefundPriceList;

	public List<Leg> getLegList() {
		return legList;
	}

	public void setLegList(List<Leg> legList) {
		this.legList = legList;
	}

	public List<Passenger> getPassengerList() {
		return passengerList;
	}

	public void setPassengerList(List<Passenger> passengerList) {
		this.passengerList = passengerList;
	}

	public List<PassengerChangePrice> getPassengerChangePriceList() {
		return passengerChangePriceList;
	}

	public void setPassengerChangePriceList(List<PassengerChangePrice> passengerChangePriceList) {
		this.passengerChangePriceList = passengerChangePriceList;
	}

	public List<PassengerRefundPrice> getPassengerRefundPriceList() {
		return passengerRefundPriceList;
	}

	public void setPassengerRefundPriceList(List<PassengerRefundPrice> passengerRefundPriceList) {
		this.passengerRefundPriceList = passengerRefundPriceList;
	}
	
	
}
