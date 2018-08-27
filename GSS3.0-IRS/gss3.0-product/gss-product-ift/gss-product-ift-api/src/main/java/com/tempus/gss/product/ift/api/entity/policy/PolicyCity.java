package com.tempus.gss.product.ift.api.entity.policy;

import java.io.Serializable;
import java.util.List;

public class PolicyCity implements Serializable {
	/**
		 * 
		 */
	private static final long serialVersionUID = -5346752498163607594L;
	private List<AirLineData> airLineData;

	public List<AirLineData> getAirLineData() {
		return airLineData;
	}

	public void setAirLineData(List<AirLineData> airLineData) {
		this.airLineData = airLineData;
	}
}
