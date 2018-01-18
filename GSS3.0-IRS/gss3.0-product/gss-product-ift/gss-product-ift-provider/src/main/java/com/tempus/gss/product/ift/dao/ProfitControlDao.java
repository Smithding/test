package com.tempus.gss.product.ift.dao;

import com.tempus.gss.product.ift.api.entity.ProfitControl;
import com.tempus.gss.product.ift.api.entity.vo.ProfitControlVo;

public interface ProfitControlDao extends BaseDao<ProfitControl, ProfitControlVo> {
	public int deleteByPolicyCabinNo(Long policyCabinNo);

	int deleteByPolicyNo(Long policyNo);
}