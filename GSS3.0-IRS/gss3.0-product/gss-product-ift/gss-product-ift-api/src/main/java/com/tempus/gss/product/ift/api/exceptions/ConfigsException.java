package com.tempus.gss.product.ift.api.exceptions;

import com.tempus.gss.product.ift.api.entity.exception.Errors;

public class ConfigsException extends SettingException{


/** SVUID */
	private static final long serialVersionUID = 1L;

	/** 异常触动模块 */
	protected static final String MODULE = "IFT.SETTING.CFG";

	/**
	 * 构造方法
	 */
	@Deprecated
	public ConfigsException() {
		super();
	}

	/**
	 * 构造方法
	 *
	 * @param errors 错误信息
	 */
	public ConfigsException(Errors errors) {
		this(MODULE, errors.getCode(), errors.getMessage(), null);
	}

	/**
	 * 构造方法
	 *
	 * @param errors 错误信息
	 * @param description 错误描述
	 */
	public ConfigsException(Errors errors, String description) {
		this(MODULE, errors.getCode(), errors.getMessage(), description);
	}

	/**
	 * 构造方法
	 *
	 * @param module 模块
	 * @param code 错误代码
	 * @param message 错误消息
	 * @param description 错误描述
	 */
	public ConfigsException(String module, String code, String message, String description) {
		super(module, code, message, description);
	}

}
