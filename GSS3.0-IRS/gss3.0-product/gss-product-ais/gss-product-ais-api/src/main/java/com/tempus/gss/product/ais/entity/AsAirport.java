package com.tempus.gss.product.ais.entity;

import java.util.Date;

/**
 * 机场_机场服务中间表
 */
public class AsAirport {
	
	private Long id;
	/**
	 * 国家id
	 */
	private String countryCode;
	/**
	 * 机场三字码
	 */
	private String airportCode;
	/**
	 * 机场服务编号
	 */
	private Long airportServiceNo;
	/**
	 * 创建日期
	 */
	private Date createTime;
	/**
	 * 修改日期
	 */
	private Date modifyTime;
	/**
	 * 创建人
	 */
	private String creator;
	/**
	 * 修改人
	 */
	private String modifier;
}
