package com.tempus.gss.product.ais.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 机场服务产品订单中间表
 */
public class AsAsOrder {
	
	private Long id;
	/**
	 * 机场服务订单编号
	 */
	private Long airportServiceOrderNo;
	/**
	 * 机场服务编号
	 */
	private Long airportServiceNo;
	/**
	 * 服务名
	 */
	private String name;
	/**
	 * 服务产品描述
	 */
	private String description;
	/**
	 * 服务数量
	 */
	private Integer count;
	/**
	 * 原采购价
	 */
	private BigDecimal buyPrice;
	/**
	 * 原销售价
	 */
	private BigDecimal salePrice;
	/**
	 * 应付款
	 */
	private BigDecimal sellPrice;
	/**
	 * 服务提供状态
	 */
	private Long service_status;
	/**
	 * 服务提供地点
	 */
	private String servicePlace;
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
	 * 删除标志 0 无效 已删除 1 有效
	 */
	private Boolean valid;
	
}

