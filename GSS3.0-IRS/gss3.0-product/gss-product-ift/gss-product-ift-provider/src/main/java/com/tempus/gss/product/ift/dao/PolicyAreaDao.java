package com.tempus.gss.product.ift.dao;

import com.tempus.gss.product.ift.api.entity.PolicyArea;
import com.tempus.gss.product.ift.api.entity.vo.PolicyAreaVo;

import java.util.List;

/**
 * @Date 2016年10月14日    上午11:36:03
 */
public interface PolicyAreaDao extends BaseDao<PolicyArea, PolicyAreaVo> {
	
	/**
	 * 查询区域是否唯一
	 * @param policyArea
	 * @return
	 */
	public PolicyArea selectExistPolicyArea(PolicyArea policyArea);

	/**
	 * 模糊检索获取数据
	 * @param param
	 * @return
	 */
	List<PolicyArea> getPolicyAreaListByParam(String param);
}

