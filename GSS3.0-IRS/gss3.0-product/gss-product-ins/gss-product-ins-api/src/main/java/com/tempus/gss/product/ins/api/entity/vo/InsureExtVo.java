/**
 * InsureExtVo.java
 * com.tempus.gss.product.ins.api.entity.vo
 *
 * Function： TODO
 *
 * ver date author
 * ──────────────────────────────────
 * 2016年11月22日 shuo.cheng
 *
 * Copyright (c) 2016, TNT All Rights Reserved.
 */

package com.tempus.gss.product.ins.api.entity.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * InsureExtVo 保险扩展信息字段
 *
 * @author   shuo.cheng
 * @version  
 * @since    Ver 1.1
 * @Date	 2016年11月22日		下午3:41:44
 *
 * @see 	 
 *  
 */
@JsonInclude(Include.NON_NULL)
public class InsureExtVo implements Serializable {

	/**
	 * serialVersionUID:序列ID
	 *
	 * @since Ver 1.1
	 */

	private static final long serialVersionUID = -8887733182253921125L;

	/**
	 * 航班号
	 */
	private String flightNo;

	/**
	 * 航班信息
	 */
	@JsonProperty("flightInfo")
	private FlightInfoVo flightInfoVo;

	public FlightInfoVo getFlightInfoVo() {

		return flightInfoVo;
	}

	public void setFlightInfoVo(FlightInfoVo flightInfoVo) {

		this.flightInfoVo = flightInfoVo;
	}

	public String getFlightNo() {

		return flightNo;
	}

	public void setFlightNo(String flightNo) {

		this.flightNo = flightNo;
	}
}

