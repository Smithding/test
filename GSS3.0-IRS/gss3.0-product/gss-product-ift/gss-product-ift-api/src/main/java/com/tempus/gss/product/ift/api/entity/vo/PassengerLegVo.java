package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;

public class PassengerLegVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*乘客编号*/
	@JsonSerialize(using = LongSerializer.class)
	private Long passengerNo;
	/*航程编号*/
	@JsonSerialize(using = LongSerializer.class)
	private Long legNo;

	public Long getPassengerNo() {

		return passengerNo;
	}

	public void setPassengerNo(Long passengerNo) {

		this.passengerNo = passengerNo;
	}

	public Long getLegNo() {

		return legNo;
	}

	public void setLegNo(Long legNo) {

		this.legNo = legNo;
	}
}
