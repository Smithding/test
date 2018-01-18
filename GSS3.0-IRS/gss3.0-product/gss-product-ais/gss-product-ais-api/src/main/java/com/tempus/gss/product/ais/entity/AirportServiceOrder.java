package com.tempus.gss.product.ais.entity;

import java.util.Date;

/**
 * 机场服务订单
 */
public class AirportServiceOrder {

	private Long id;
	/**
	 * 机场服务订单编号
	 */
	private Long airportServiceOrderNo;
	/**
	 * 机票订单id
	 */
	private Long ticketId;
	/**
	 * 机票订单状态
	 */
	private Long ticketStatus;
	/**
	 * 订单号
	 */
	private Long orderNo;
	/**
	 * 启用状态
	 */
	private Boolean status;
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
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 删除标志 0 无效 已删除 1 有效
	 */
	private Boolean valid;

}
