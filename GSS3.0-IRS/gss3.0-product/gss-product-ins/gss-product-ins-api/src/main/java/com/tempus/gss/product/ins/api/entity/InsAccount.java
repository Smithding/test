package com.tempus.gss.product.ins.api.entity;

import java.util.Date;

/**
 * 投保账号配置.
 */
public class InsAccount {

	private Long id;
	/**
	 * 数据归属
	 */
	private Integer owner;

	/**
	 * 账号编号.
	 */
	private Long accountNo;

	/**
	 * 所属渠道.
	 * 1：腾邦保险经纪公司.
	 */
	private Integer channelName;

	/**
	 * 账号.
	 */
	private String accountName;

	/**
	 * 账号标识.
	 * 加密后保存.
	 */
	private String token;

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
