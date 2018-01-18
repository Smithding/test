package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;

/**
 * 政策查询参数.
 */
public class PolicyQuery implements Serializable {

	/**
	 * 出票航司.
	 */
	private String airline;

	/**
	 * 出票日期.
	 */
	private String ticketDate;

	/**
	 * 去程起点.
	 */
	private String goStart;

	/**
	 * 去程终点.
	 */
	private String goEnd;

	/**
	 * 投放经销商.
	 */
	private String distributor;

	/**
	 * 产品商.
	 */
	private String productor;

	/**
	 * 销售配置.
	 */
	private String saleConfig;

	/**
	 * 产品号     .
	 */
	private String productNo;

	/**
	 * 产品ID     .
	 */
	private Long policyNo;

	/**
	 * 状态
	 */
	private Integer Status;
}
