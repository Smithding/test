package com.tempus.gss.product.ift.dao;

import com.tempus.gss.product.ift.api.entity.DemandPassenger;
import com.tempus.gss.product.ift.api.entity.vo.DemandPassengerVo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DemandPassengerDao extends BaseDao<DemandPassenger, DemandPassengerVo> {

	/*
	* 通过订单编号获取需求单乘客
	* */
	public List<DemandPassenger> selectByDemandNo(Long DemandNo);

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