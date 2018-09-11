package com.tempus.gss.product.ift.api.entity.policy;

import java.io.Serializable;
import java.util.List;

import com.tempus.gss.product.ift.api.entity.CabinsPricesTotals;

/**
 * 订单预订页面政策
 * @author juan.yin
 *
 */
public class IftPolicyChange implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 航线仓位价格总和.
     */
    private List<CabinsPricesTotals> cabinsPricesTotalses;
    /**
     * 订单预订页面政策
     */
    private IftFlightPolicy flightPolicy;
	public List<CabinsPricesTotals> getCabinsPricesTotalses() {
		return cabinsPricesTotalses;
	}
	public void setCabinsPricesTotalses(List<CabinsPricesTotals> cabinsPricesTotalses) {
		this.cabinsPricesTotalses = cabinsPricesTotalses;
	}
	public IftFlightPolicy getFlightPolicy() {
		return flightPolicy;
	}
	public void setFlightPolicy(IftFlightPolicy flightPolicy) {
		this.flightPolicy = flightPolicy;
	}
}
