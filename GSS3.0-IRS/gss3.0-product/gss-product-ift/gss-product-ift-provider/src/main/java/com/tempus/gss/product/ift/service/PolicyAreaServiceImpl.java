package com.tempus.gss.product.ift.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.PolicyArea;
import com.tempus.gss.product.ift.api.entity.vo.PolicyAreaVo;
import com.tempus.gss.product.ift.api.service.IPolicyAreaService;
import com.tempus.gss.product.ift.dao.PolicyAreaDao;
import com.tempus.gss.system.service.IMaxNoService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 自定义区域
 */
@Service
@EnableAutoConfiguration
public class PolicyAreaServiceImpl  implements IPolicyAreaService {
	protected final transient Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	PolicyAreaDao policyAreaDao;
	@Reference
	private IMaxNoService maxNoService;


	/**
	 * 创建自定义区域
	 */
	@Override
	@Transactional
	public int createPolicyArea(RequestWithActor<PolicyArea> policyArea) {
		// 生成编号
		Long PolicyAreaNo = maxNoService.generateBizNo("IFT_POLICY_AREA_NO", 45);
		policyArea.getEntity().setPolicyAreaNo(PolicyAreaNo);
		int price = policyAreaDao.insert(policyArea.getEntity());
		if (price == 0) {
			log.error("创建时异常========");
			throw new GSSException("自定义区域创建模块", "0102", "创建发生异常,请检查");
		}
		log.info("创建自定义区域结束==========");
		return 1;
	}

	@Override
	@Transactional
	public int updatePolicyArea(RequestWithActor<PolicyArea> policyArea) {

		log.info("修改自定义区域开始");
		if (policyArea.getEntity().getPolicyAreaNo() == null) {
			throw new GSSException("自定义区域修改模块", "0201", "请输入一个区域编号-------");
		}
		int price = policyAreaDao.updateByPrimaryKeySelective(policyArea.getEntity());
		if (price == 0) {
			log.error("修改发生异常========");
			throw new GSSException("自定义区域修改模块", "0202", "修改发生异常，请检查");
		}
		return 1;
	}

	@Override
	@Transactional
	public int deletePolicyArea(RequestWithActor<PolicyArea> policyArea) {

		log.info("删除开始");
		PolicyArea pa = policyAreaDao.selectByPrimaryKey(policyArea.getEntity().getPolicyAreaNo());
		if (pa == null) {
			throw new GSSException("自定义区域删除模块", "00302", "此编号不存在或为null-------");
		}
		if (pa.getValid() == true) {
			policyArea.getEntity().setValid(false);
		} else {
			throw new GSSException("自定义区域删除模块", "00303", "此数据已经处于删除状态");
		}
		int price = policyAreaDao.updateByPrimaryKeySelective(policyArea.getEntity());
		if (price == 0) {
			log.error("删除发生异常========");
			throw new GSSException("自定义区域删除模块", "00304", "删除发生异常，请检查");
		}
		log.info("删除结束");
		return price;
	}

	@Override
	public PolicyArea selectPolicyArea(RequestWithActor<Long> policyArea) {

		log.info("查询开始--------");
		PolicyArea policyarea = null;
		if (policyArea.getEntity() != null) {
			policyarea = policyAreaDao.selectByPrimaryKey(policyArea.getEntity());
		}
		log.info("查询结束--------");
		return policyarea;
	}

	@Override
	public Page<PolicyArea> queryPolicyAreaList(Page<PolicyArea> page, RequestWithActor<PolicyAreaVo> requestWithActor) {

		log.info("查询开始");
		try {
			List<PolicyArea> policyAreaList = policyAreaDao.queryObjByKey(page, requestWithActor.getEntity());
			page.setRecords(policyAreaList);
		} catch (Exception e) {
			log.error("自定义区域表中未查询出相应的结果集", e);
			throw new GSSException("查询自定义区域信息模块", "0022", "查询自定义区域失败");
		}
		log.info("查询结束");
		return page;
	}

	@Override
	public PolicyArea selectExistPolicyArea(PolicyArea policyArea) {
		return policyAreaDao.selectExistPolicyArea(policyArea);
	}

	@Override
	public List<PolicyArea> getPolicyAreaListByParam(String param) {
		log.info("查询区域，入参：param="+param);
		return policyAreaDao.getPolicyAreaListByParam(param);
	}

}
