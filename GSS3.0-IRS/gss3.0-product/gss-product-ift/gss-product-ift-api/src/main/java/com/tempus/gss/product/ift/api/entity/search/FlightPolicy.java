package com.tempus.gss.product.ift.api.entity.search;

import java.io.Serializable;

import com.tempus.gss.product.ift.api.entity.policy.IftPolicy;

public class FlightPolicy implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 1标示去程 2标识回程
	 */
	private Integer flightNum;
	/**
	 * 政策信息
	 */
	private IftPolicy iftPolicy;
	public Integer getFlightNum() {
		return flightNum;
	}
	public void setFlightNum(Integer flightNum) {
		this.flightNum = flightNum;
	}
	public IftPolicy getIftPolicy() {
		return iftPolicy;
	}
	public void setIftPolicy(IftPolicy iftPolicy) {
		this.iftPolicy = iftPolicy;
	}
}
