package com.tempus.gss.product.ift.dao.policy;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.tempus.gss.product.ift.api.entity.policy.IftOrderPolicy;

/**
 * 
 * <pre>
 * <b>国际订单政策数据库操作接口.</b>
 * <b>Description:</b> 
 *    
 * <b>Author:</b> cz
 * <b>Date:</b> 2018年9月3日 上午10:27:30
 * <b>Copyright:</b> Copyright ©2018 tempus.cn. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年9月3日 上午10:27:30    cz
 *         new file.
 * </pre>
 */
public interface IftOrderPolicyMapper extends AutoMapper<IftOrderPolicy> {

	/**
	 * 添加国际订单政策
	 * 
	 * @param iftOrderPolicy 国际订单政策
	 * @return int 保存记录数
	 */
	int insert(IftOrderPolicy iftOrderPolicy);
	
}
