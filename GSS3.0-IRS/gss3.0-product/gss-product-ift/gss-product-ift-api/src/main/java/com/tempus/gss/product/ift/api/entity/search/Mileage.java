package com.tempus.gss.product.ift.api.entity.search;

import java.io.Serializable;

public class Mileage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 航程标识 1去程，2回程
	 */
	private int flightNum;
	/**
	 * 总里程
	 */
	public int totalMileage;
	/**
	 * 共享段里程
	 */
	public int shareMileage;
	/**
	 * 非共享段里程
	 */
	public int notShareMileage;
	public int getTotalMileage() {
		return totalMileage;
	}
	public void setTotalMileage(int totalMileage) {
		this.totalMileage = totalMileage;
	}
	public int getShareMileage() {
		return shareMileage;
	}
	public void setShareMileage(int shareMileage) {
		this.shareMileage = shareMileage;
	}
	public int getNotShareMileage() {
		return notShareMileage;
	}
	public void setNotShareMileage(int notShareMileage) {
		this.notShareMileage = notShareMileage;
	}
	public int getFlightNum() {
		return flightNum;
	}
	public void setFlightNum(int flightNum) {
		this.flightNum = flightNum;
	}
}
