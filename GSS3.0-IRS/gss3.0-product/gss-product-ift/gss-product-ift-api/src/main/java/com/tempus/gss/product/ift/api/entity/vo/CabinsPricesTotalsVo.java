package com.tempus.gss.product.ift.api.entity.vo;

import com.tempus.gss.product.ift.api.entity.PassengerTypePricesTotal;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangwei on 2016/10/13.
 */
public class CabinsPricesTotalsVo implements Serializable {

	/**
	 * 航线仓位集合.
	 */
	private String cabins;
	/**
	 * 航线仓位数量.
	 */
	private Integer cabinsCount;


	/**
	 * 票规
	 */
	private String ticketRule;



	private List<PassengerTypePricesTotalVo> passengerTypePricesTotals;

	public String getCabins() {

		return cabins;
	}

	public void setCabins(String cabins) {

		this.cabins = cabins;
	}

	public Integer getCabinsCount() {

		return cabinsCount;
	}

	public void setCabinsCount(Integer cabinsCount) {

		this.cabinsCount = cabinsCount;
	}

	public List<PassengerTypePricesTotalVo> getPassengerTypePricesTotals() {

		return passengerTypePricesTotals;
	}

	public void setPassengerTypePricesTotals(
			List<PassengerTypePricesTotalVo> passengerTypePricesTotals) {

		this.passengerTypePricesTotals = passengerTypePricesTotals;
	}

	public String getTicketRule() {

		return ticketRule;
	}

	public void setTicketRule(String ticketRule) {

		this.ticketRule = ticketRule;
	}
}
