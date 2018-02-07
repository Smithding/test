package com.tempus.gss.product.ift.api.entity.exception;


/**
 * <pre>
 * <b>产品异常.</b>
 * <b>Description:</b>
 * </pre>
 */
public class ProductException extends TicketException {

	/** SVUID */
	private static final long serialVersionUID = 1L;

	/** 异常触动模块 */
	protected static final String MODULE = "DPS.PRODUCT";

	/**
	 * 构造方法
	 */
	@Deprecated
	public ProductException() {
	}

	/**
	 * 构造方法
	 *
	 * @param errors 错误信息
	 */
	public ProductException(Errors errors) {
		this(errors, null);
	}

	/**
	 * 构造方法
	 *
	 * @param errors 错误信息
	 * @param description 错误描述
	 */
	public ProductException(Errors errors, String description) {
		super(MODULE, errors.getCode(), errors.getMessage(), description);
	}
}
