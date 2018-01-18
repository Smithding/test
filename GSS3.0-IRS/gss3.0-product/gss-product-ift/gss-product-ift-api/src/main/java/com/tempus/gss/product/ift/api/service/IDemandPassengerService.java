package com.tempus.gss.product.ift.api.service;

import com.tempus.gss.product.ift.api.entity.DemandPassenger;

/**
 * 国际机票需求单服务接口.
 */
public interface IDemandPassengerService  {

	/**
	 * 修改valid
	 *
	 * @param record
	 * @return
	 */
	int updateValid(DemandPassenger record);

	/**
	 * 修改status（挂起解挂使用）
	 *
	 * @param record
	 * @return
	 */
	int updateStatus(DemandPassenger record);
}
