package com.tempus.gss.product.ift.api.service.policy;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.ift.api.entity.policy.IftPolicy;
import com.tempus.gss.product.ift.api.entity.policy.IftPolicyQuery;
import com.tempus.gss.vo.Agent;

/**
 * 
 * <pre>
 * <b>国际政策服务接口.</b>
 * <b>Description:</b> 
 *    
 * <b>Author:</b> cz
 * <b>Date:</b> 2018年8月27日 下午1:54:17
 * <b>Copyright:</b> Copyright ©2018 tempus.cn. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年8月27日 下午1:54:17    cz
 *         new file.
 * </pre>
 */
public interface IftPolicyService {
	
	/**
	 * 创建国际政策
	 * 
	 * @param agent 用户信息
	 * @param iftPolicy 政策信息
	 * @return long 政策ID
	 */
	long create(Agent agent, IftPolicy iftPolicy);
	
	/**
	 *  根据政策ID获取政策信息
	 *  
	 * @param agent 用户信息
	 * @param policyId 政策ID
	 * @return IftPolicy 国际政策
	 */
	IftPolicy find(Agent agent, long policyId);
	
	/**
	 *  分页查询国际政策信息
	 * 
	 * @param agent 用户信息
	 * @param query 查询条件实体
	 * @return Page<IftPolicy> 分页国际政策数据
	 */
	Page<IftPolicy> search(Agent agent, Page<IftPolicy> page, IftPolicyQuery query);
	
	/**
	 * 	编辑国际政策信息
	 * 
	 * @param agent 用户信息
	 * @param iftPolicy 国际政策信息
	 * @return long 编辑成功后新政策ID
	 */
	long update(Agent agent, IftPolicy iftPolicy);
	
	/**
	 *  设置政策为无效
	 * 
	 * @param agent 用户信息
	 * @param policyId 国际政策ID
	 * @return boolean 是否成功
	 */
	boolean setInvalid(Agent agent, long policyId);
	
	/**
	 *  修改国际政策状态
	 * 
	 * @param agent 用户信息
	 * @param status 状态:1:待审核(默认); 2:已审核; 3:启用; 4:禁用;
	 * @param policyId 国际政策ID集合
	 * @return boolean 是否成功
	 */
	boolean changeStatus(Agent agent, Integer status, Long... policyId);
}
