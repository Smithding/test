package com.tempus.gss.product.ift.service.policy;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.tempus.gss.product.ift.api.entity.policy.IftPolicy;
import com.tempus.gss.product.ift.api.service.policy.IftPolicyService;
import com.tempus.gss.product.ift.dao.policy.IftPolicyMapper;
import com.tempus.gss.util.EntityUtil;
import com.tempus.gss.vo.Agent;

/**
 * 
 * <pre>
 * <b>国际政策服务.</b>
 * <b>Description:</b> 
 *    
 * <b>Author:</b> cz
 * <b>Date:</b> 2018年8月27日 下午2:34:13
 * <b>Copyright:</b> Copyright ©2018 tempus.cn. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年8月27日 下午2:34:13    cz
 *         new file.
 * </pre>
 */
@Service
public class IftPolicyServiceImpl implements IftPolicyService {
	
	@Autowired
	private IftPolicyMapper iftPolicyMapper;
		
	@Override
	public long create(Agent agent, IftPolicy iftPolicy) {
		long policyId = IdWorker.getId();
		iftPolicy.setId(policyId);
		EntityUtil.setAddInfo(iftPolicy, agent);
		iftPolicyMapper.insert(iftPolicy);
		return policyId;
	}
	
}
