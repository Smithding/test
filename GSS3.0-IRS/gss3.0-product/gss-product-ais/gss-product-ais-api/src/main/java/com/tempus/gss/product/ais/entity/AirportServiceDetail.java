package com.tempus.gss.product.ais.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.tempus.gss.order.entity.SaleOrder;

/**
 * 机场服务明细
 */
public class AirportServiceDetail {

	private Long id;
	/**
	 * 机场服务订单明细编号
	 */
	private Long airportServiceDetailNo;
	/**
	 * 数据归属单位
	 */
	private Integer owner;
	/**
	 * 服务产品编号
	 */
	private Long airportServiceNo;
	/**
	 * 销售单编号
	 */
	private Long saleOrderNo;
	/**
	 * 票价
	 */
	private BigDecimal fare;
	/**
	 * 手续费
	 */
	private BigDecimal brokerage;
	/**
	 * 联系人姓名
	 */
	private String contactName;

	/**
	 * 证件类型
	 */
	private String certType;

	/**
	 * 证件号码
	 */
	private String certNo;

	/**
	 * 性别
	 */
	private Integer gender;

	/**
	 * 联系人电话
	 */
	private String contactPhone;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 启用状态
	 */
	private Boolean status;
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	/**
	 * 创建人
	 */
	private String creator;
	/**
	 * 操作人 默认为：sys
	 */
	private String modifier;
	/**
	 * 删除标志 0 无效 已删除 1 有效
	 */
	private Boolean valid;
	/**
	 * 销售单
	 */
	private SaleOrder saleorder;
}
