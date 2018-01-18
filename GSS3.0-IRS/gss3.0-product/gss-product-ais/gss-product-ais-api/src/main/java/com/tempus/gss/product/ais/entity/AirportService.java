package com.tempus.gss.product.ais.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 机场服务产品
 */
public class AirportService {

	private Long id;
	/**
	 * 服务产品编号
	 */
	private Long airportServiceNo;
	/**
	 * 服务产品名称
	 */
	private String Name;
	/**
	 * 服务产品描述
	 */
	private String description;
	/**
	 * 采购价
	 */
	private BigDecimal buyPrice;
	/**
	 * 销售价
	 */
	private BigDecimal salePrice;
	/**
	 * 状态
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
