package com.tempus.gss.product.ift.dao.policy;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.tempus.gss.product.ift.api.entity.policy.IftPolicy;

/**
 * 
 * <pre>
 * <b>国际政策数据库操作接口.</b>
 * <b>Description:</b> 
 *    
 * <b>Author:</b> cz
 * <b>Date:</b> 2018年8月27日 下午2:38:05
 * <b>Copyright:</b> Copyright ©2018 tempus.cn. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年8月27日 下午2:38:05    cz
 *         new file.
 * </pre>
 */
public interface IftPolicyMapper extends AutoMapper<IftPolicy> {

	/**
	 * 添加国际政策
	 * 
	 * @param iftPolicy 国际政策信息
	 * @return int 保存记录数
	 */
	int insert(IftPolicy iftPolicy);
	
	/**
	 * 根据ID获取政策详情信息
	 * 
	 * @param owner 归集单位
	 * @param policyId 政策ID
	 * @return IftPolicy 政策详情
	 */
	IftPolicy find(@Param("owner") int owner,  @Param("policyId") long policyId);
}
