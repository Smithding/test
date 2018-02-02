package com.tempus.gss.product.ift.dao;

import com.tempus.gss.product.ift.api.entity.DemandDetail;
import com.tempus.gss.product.ift.api.entity.vo.DemandDetailVo;

import java.util.List;

/**
 * Created by Administrator on 2016/9/27 0027.
 */
public interface DemandDetailDao extends BaseDao<DemandDetail, DemandDetailVo> {

	/**
	 * 根据demandNo查询数据
	 *
	 * @param l
	 * @return
	 */
	public List<DemandDetail> selectByDemandNo(Long l);

	/**
	 * 修改valid
	 *
	 * @param record
	 * @return
	 */
	int updateValid(DemandDetail record);

	/**
	 * 修改status（挂起解挂使用）
	 *
	 * @param record
	 * @return
	 */
	int updateStatus(DemandDetail record);
}
