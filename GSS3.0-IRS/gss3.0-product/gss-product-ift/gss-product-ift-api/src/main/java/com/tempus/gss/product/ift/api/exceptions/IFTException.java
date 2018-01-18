package com.tempus.gss.product.ift.api.exceptions;

import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.ift.api.entity.exception.Errors;

public class IFTException extends GSSException{


/** SVUID */
	private static final long serialVersionUID = 1L;

	/** 异常触动模块 */
	protected static final String MODULE = "IFT";

	/**
	 * 构造方法
	 */
	@Deprecated
	public IFTException() {
		super();
	}

	/**
	 * 构造方法
	 *
	 * @param errors 错误信息
	 */
	public IFTException(Errors errors) {
		this(MODULE, errors.getCode(), errors.getMessage(), null);
	}

	/**
	 * 构造方法
	 *
	 * @param errors 错误信息
	 * @param description 错误描述
	 */
	public IFTException(Errors errors, String description) {
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
	public IFTException(String module, String code, String message, String description) {
		super(module, code, message, description);
	}

}
