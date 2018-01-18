package com.tempus.gss.product.ift.dao;

import com.tempus.gss.product.ift.api.entity.Policy;
import com.tempus.gss.product.ift.api.entity.vo.PolicyVo;
import java.util.List;

public interface PolicyDao extends BaseDao<Policy, PolicyVo> {

	/**
	 * 修改valid
	 *
	 * @param record
	 * @return
	 */
	int updateValid(Policy record);

	/**
	 * 修改status（挂起解挂使用）
	 *
	 * @param record
	 * @return
	 */
	int updateStatus(Policy record);


	List<Policy> queryObjByOD (Policy record);
	
	
	Policy selectByPlolicyId(Long policyId);
	
	/**
	 * 批量添加产品的信息
	 * @param list 信息
	 * @return int 添加数据最后一行ID
	 */
	void insertByList(List<Policy> list);
	
	/**
	 * 查询列表
	 * @param policy
	 * @return List<Policy>
	 */
	List<Policy> getByPolicyIds(Policy policy);
}