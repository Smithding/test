package com.tempus.gss.product.ift.api.entity.exception;

/**
 * <pre>
 * <b>产品模块异常类.</b>
 * <b>Description:</b>
 *
 * <b>Author:</b> wlh
 * <b>Date:</b> 2016年10月12日 下午3:39:42
 * <b>Copyright:</b> Copyright ©2016 tempus.cn. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2016年10月14日 下午3:25:40    cz
 *         new file.
 * </pre>
 */
public enum Errors {

	/** E_UNKNOWN: 未知异常 */
	E_UNKNOWN("未知异常"),

	/** E_INVALID_PARAM: 无效的参数 */
	E_INVALID_PARAM("无效的参数"),

	/** E_LAST_REBATE_PROFIT_EXIST: 后返控润已存在 */
	E_LAST_REBATE_PROFIT_EXIST("后返控润已存在"),

	/** 终端信息为空 */
	E_AGENTNULL("终端信息为空"),

	/** 未查询到数据 */
	E_NODATA("未查询到数据"),

	/** 无效ID */
	E_INVALID("无效ID"),

	/** 实体为空 */
	E_NULLENTITY("实体为null"),

	/** 只有非启用状态才能进行编辑和删除操作 */
	E_PARTINVALID("只有非启用状态才能进行编辑和删除操作"),

	/** 执行数据库操作失败 */
	E_EXECUTEFAILURE("执行数据库操作失败"),

	/** 记录已存在 */
	E_RECORDEXIST("记录已存在"),
	
	/** 处理失败 */
	E_PROCESSFAILURE("处理失败"),
	/** 配置正在使用中，不允许修改 */
	E_DEFAULT("已存在该航司的全国默认政策"),
	/***/
	E_RULEUSING("配置正在使用中，不允许修改"),
	/** 不存在的officeNo*/
	E_SUPPERERROE("根据officeNo没有找到对应的供应商数据");
	/**
	 * 错误消息
	 */
	private String message;

	/**
	 * 构造方法.
	 *
	 * @param message
	 */
	Errors(String message) {
		this.message = message;
	}

	/**
	 * 获取 错误编码
	 *
	 * @return String
	 */
	public String getCode() {
		return this.name();
	}

	/**
	 * 获取 错误所在模块
	 *
	 * @return String
	 */
	public String getMessage() {
		return message;
	}

}
