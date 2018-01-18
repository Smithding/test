package com.tempus.gss.product.ift.dao;

import com.tempus.gss.product.ift.api.entity.Demand;
import com.tempus.gss.product.ift.api.entity.vo.DemandVo;

/**
 * Created by Administrator on 2016/9/27 0027.
 */
public interface DemandDao extends BaseDao<Demand, DemandVo> {

	/**
	 * 修改valid
	 *
	 * @param record
	 * @return
	 */
	int updateValid(Demand record);

	/**
	 * 修改status（挂起解挂使用）
	 *
	 * @param record
	 * @return
	 */
	int updateStatus(Demand record);

	/**
	 * 根据查询条件统计需求单数量
	 * @param demandVo
	 * @return
	 */
	Long countDemand(DemandVo demandVo);
}
