package com.tempus.gss.product.ift.dao;

import java.util.List;

import com.tempus.gss.product.ift.api.entity.Cabin;
import com.tempus.gss.product.ift.api.entity.vo.CabinVo;

public interface CabinDao extends BaseDao<Cabin, CabinVo> {
	/**
	 * 根据policyNo删除舱位表
	 *
	 * @param policyNo
	 * @return int
	 */
	public int deleteByPolicyNo(Long policyNo);
	/**
	 * 根据policyNo查询舱位表
	 *
	 * @param policyNo
	 * @return List<Cabin>
	 */
	public List<Cabin> selectByPolicyNo(Long policyNo);
}