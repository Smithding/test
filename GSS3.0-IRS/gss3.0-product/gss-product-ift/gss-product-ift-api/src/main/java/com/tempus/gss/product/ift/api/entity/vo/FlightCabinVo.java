package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 航班舱位.
 */
public class FlightCabinVo implements Serializable {

	/**
	 * 舱位.
	 */
	private String cabin;

	/**
	 * 服务等级.
	 */
	private String grade;

	/**
	 * 剩余座位数.
	 * 99表示充足。其他数值表示实际数量.
	 */
	private int seatCount;

	/**
	 * 舱位报价.
	 */
	private List<FlightCabinPriceVo> cabinPriceVoList;

	public String getCabin() {

		return cabin;
	}

	public void setCabin(String cabin) {

		this.cabin = cabin;
	}

	public String getGrade() {

		return grade;
	}

	public void setGrade(String grade) {

		this.grade = grade;
	}

	public int getSeatCount() {

		return seatCount;
	}

	public void setSeatCount(int seatCount) {

		this.seatCount = seatCount;
	}

	public List<FlightCabinPriceVo> getCabinPriceVoList() {

		return cabinPriceVoList;
	}

	public void setCabinPriceVoList(List<FlightCabinPriceVo> cabinPriceVoList) {

		this.cabinPriceVoList = cabinPriceVoList;
	}

}
