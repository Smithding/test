package com.tempus.gss.product.hol.api.syn;

public interface IAsyncHolDoTask {
	/**
	 * 更新同程酒店基本信息表
	 */
	public void updateResBaseInfo(Long resId, Integer minPrice);
	/**
	 * 更新最低价和中间表最低价
	 * @param resId
	 * @param minPrice
	 */
	public void saveMInPriceAndMidHol(Long resId, Integer minPrice);
	/**
	 * 更新同程房型表
	 * @param resId
	 */
	public void updateResProInfo(Long resId);
}
