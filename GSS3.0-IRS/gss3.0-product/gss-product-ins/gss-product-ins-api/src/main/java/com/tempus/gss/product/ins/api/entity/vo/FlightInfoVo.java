/**
 * FlightInfoVo.java
 * com.tempus.gss.product.ins.api.entity.vo
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2016年11月25日 		shuo.cheng
 *
 * Copyright (c) 2016, TNT All Rights Reserved.
*/

package com.tempus.gss.product.ins.api.entity.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * ClassName:FlightInfoVo
 *
 * @author   shuo.cheng
 * @version  
 * @since    Ver 1.1
 * @Date	 2016年11月25日		上午9:18:29
 *
 * @see 	 
 *  
 */
@JsonInclude(Include.NON_NULL)
public class FlightInfoVo implements Serializable {

	/**
	 * serialVersionUID:序列ID
	 *
	 * @since Ver 1.1
	 */

	private static final long serialVersionUID = 1127179850019095132L;

	/**
	 * 航班号
	 */
	private String flightNo;

	/**
	 * 航班日期 格式:YYYY-MM-DD hh:mm:ss
	 */
	@JsonFormat(pattern = "yyyyMMddHHmmss", timezone = "GMT+8")
	private Date flightDate;

	/**
	 * 出发地三字码
	 */
	private String originCode;

	/**
	 * 目的地三字码
	 */
	private String destinationCode;

	/**
	 * 出发地城市名
	 */
	private String originCity;

	/**
	 * 目的地城市名
	 */
	private String destinationCity;
    
	
	/**
	 * 太平 保险专属  :限购人数
	 */
	private int quantity;
	
	/**
	 * 太平 保险专属  : 产品类型
	 * 国内tphyi260
	 * 国外tphyi120
	 */
	private String planType;


	/**
	 *行程单保险存储票号（投保需要）
	 */
	private String billNo;

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getFlightNo() {

		return flightNo;
	}

	public void setFlightNo(String flightNo) {

		this.flightNo = flightNo;
	}

	public Date getFlightDate() {

		return flightDate;
	}

	public void setFlightDate(Date flightDate) {

		this.flightDate = flightDate;
	}

	public String getOriginCode() {

		return originCode;
	}

	public void setOriginCode(String originCode) {

		this.originCode = originCode;
	}

	public String getDestinationCode() {

		return destinationCode;
	}

	public void setDestinationCode(String destinationCode) {

		this.destinationCode = destinationCode;
	}

	public String getOriginCity() {

		return originCity;
	}

	public void setOriginCity(String originCity) {

		this.originCity = originCity;
	}

	public String getDestinationCity() {

		return destinationCity;
	}

	public void setDestinationCity(String destinationCity) {

		this.destinationCity = destinationCity;
	}

}

