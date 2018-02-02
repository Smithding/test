package com.tempus.gss.product.ins.api.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * 保险公司.
 */
@Alias("insCompany")
public class Company implements Serializable {

	private Long id;
	/**
	 * 数据归属
	 */
	private Integer owner;

	/**
	 * 保险公司编号.
	 */
	private Long companyNo;

	/**
	 * 名称.
	 */
	private String name;

	/**
	 * 结算方式.
	 * 1:月结，2：现结。
	 */
	private Integer settlementType;

	/**
	 * 支付方式.
	 * 1：不限
	 */
	private Integer payType;

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
	 * 删除标记
	 */
	private Boolean valid;
}
