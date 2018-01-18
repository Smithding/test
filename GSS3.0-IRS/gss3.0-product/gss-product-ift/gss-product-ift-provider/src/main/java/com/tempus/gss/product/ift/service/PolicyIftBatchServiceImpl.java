package com.tempus.gss.product.ift.service;

import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.tempus.gss.product.ift.api.entity.PolicyIftBatch;
import com.tempus.gss.product.ift.api.entity.exception.Errors;
import com.tempus.gss.product.ift.api.entity.exception.ProductException;
import com.tempus.gss.product.ift.api.service.PolicyIftBatchService;
import com.tempus.gss.product.ift.dao.PolicyDao;
import com.tempus.gss.product.ift.dao.PolicyIftBatchDao;
import com.tempus.gss.util.EntityUtil;
import com.tempus.gss.vo.Agent;

/**
 * <pre>
* <b>产品上传 操作服务接口实现.</b>
* <b>Description:</b> 
 */
@Service
public class PolicyIftBatchServiceImpl implements PolicyIftBatchService {

	@Autowired
	protected PolicyIftBatchDao mapper;

	@Autowired
	protected PolicyDao policyMapper;

	/** 日志记录器. */
	protected static final Logger logger = LogManager.getLogger(PolicyIftBatchServiceImpl.class);

	@Override
	@Transactional
	public PolicyIftBatch get(Agent agent, Long id) throws ProductException {
		if (null == id || 0L >= id) {
			throw new ProductException(Errors.E_INVALID_PARAM);
		}

		PolicyIftBatch policyBatch = mapper.find(agent.getOwner(), id);
		if (null == policyBatch) {
			throw new ProductException(Errors.E_INVALID);
		}
		return policyBatch;
	}

	@Override
	@Transactional
	public long create(Agent agent, PolicyIftBatch policyBatch) throws ProductException {
		Long idTemp = IdWorker.getId();
		policyBatch.setId(idTemp);
		EntityUtil.setAddInfo(policyBatch, agent); // 添加创建者信息
		mapper.add(policyBatch);
		if (logger.isInfoEnabled()) {
			logger.info(agent.getAccount() + "," + agent.getIp() + "," + " 上传产品文件 " + policyBatch.getId());
		}
		return policyBatch.getId();
	}

	@Override
	@Transactional
	public void modify(Agent agent, PolicyIftBatch policyBatch) throws ProductException {
		// 设置修改者信息
		policyBatch.setModifier(agent.getAccount());
		policyBatch.setModifyTime(new Date());
		mapper.update(policyBatch);
		if (logger.isInfoEnabled()) {
			logger.info(agent.getAccount() + "," + agent.getIp() + "," + " 修改产品文件 " + policyBatch.getId());
		}
	}

	@Override
	@Transactional
	public void remove(Agent agent, Long... id) throws ProductException {
		if (ArrayUtils.isEmpty(id)) {
			throw new ProductException(Errors.E_INVALID_PARAM);
		}
		mapper.del(agent.getOwner(), agent.getAccount(), new Date(), id);
		if (logger.isInfoEnabled()) {
			logger.info(agent.getAccount() + "," + agent.getIp() + "," + " 删除产品文件 " + id);
		}
	}

	@Override
	@Transactional
	public Page<PolicyIftBatch> search(Agent agent, Page<PolicyIftBatch> page, Integer policyType, Long searchId, String searchName, Date searchCreateTimeStart,
			Date searchCreateTimeEnd, Integer status,String creator,String modifier) throws ProductException {
		// TODO Auto-generated method stub
		if (agent == null) {
			throw new ProductException(Errors.E_NULLENTITY);
		}
		if (null == page) {
			throw new ProductException(Errors.E_INVALID_PARAM);
		}
		int owner = agent.getOwner();
		List<PolicyIftBatch> list = mapper.query(page, owner, policyType, searchId, searchName, searchCreateTimeStart, searchCreateTimeEnd, status,creator,modifier);

		page.setRecords(list);
		return page;
	}

}
