package com.tempus.gss.product.ift.api.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.PolicyArea;
import com.tempus.gss.product.ift.api.entity.vo.PolicyAreaVo;

import java.util.List;

/**
 * 自定义区域服务接口
 */
public interface IPolicyAreaService {

	/**
	 * 创建自定义区域.
	 *
	 * @param policyArea
	 * @return
	 */
	int createPolicyArea(RequestWithActor<PolicyArea> policyArea);

	/**
	 * 修改自定义区域.
	 *
	 * @param policyArea
	 * @return
	 */
	int updatePolicyArea(RequestWithActor<PolicyArea> policyArea);

	/**
	 * 删除自定义区域.
	 *
	 * @param policyArea
	 * @return
	 */
	int deletePolicyArea(RequestWithActor<PolicyArea> policyArea);

	/**
	 * 自定义区域查询，按id查询
	 *
	 * @param policyArea
	 * @return
	 */
	PolicyArea selectPolicyArea(RequestWithActor<Long> policyArea);
	/**
	 * 自定义区域查询，全部查询
	 * 
	 */
	public Page<PolicyArea> queryPolicyAreaList(Page<PolicyArea> page,RequestWithActor<PolicyAreaVo> requestWithActor) ;
	
	/**
	 *自定义区域查询，查询是区域代码，区域名称是否存在重复
	 */
	public PolicyArea selectExistPolicyArea(PolicyArea policyArea);

	/**
	 * 模糊查询，获取数据
	 * @param param
	 * @return
	 */
	List<PolicyArea> getPolicyAreaListByParam(String param);
}
