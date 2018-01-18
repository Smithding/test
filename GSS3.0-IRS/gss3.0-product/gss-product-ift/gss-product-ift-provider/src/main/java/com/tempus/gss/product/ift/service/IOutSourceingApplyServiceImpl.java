package com.tempus.gss.product.ift.service;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.OutSourceingApply;
import com.tempus.gss.product.ift.api.entity.vo.OutSourceingApplyVo;
import com.tempus.gss.product.ift.api.service.IOutSourceingApplyService;
import com.tempus.gss.product.ift.dao.OutSourceingApplyDao;
import com.tempus.gss.util.JsonUtil;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import com.alibaba.dubbo.config.annotation.Service;


/**
 *
 * OutSourceingApply 表数据服务层接口实现类
 *
 */
@Service
@EnableAutoConfiguration
public class IOutSourceingApplyServiceImpl implements IOutSourceingApplyService {
	private final transient Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	OutSourceingApplyDao outSourceingApplyDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int create(OutSourceingApplyVo outSourceingApplyVo) {
		log.info("创建国际临时外购付款申请单开始");
		try {
			int num = outSourceingApplyDao.insertAndGetId(outSourceingApplyVo);
			if (num == 0) {
				log.error("outSourceingApplyVo插入异常");
				throw new GSSException("创建国际临时外购付款申请模块", "0102", "国际临时外购付款申请单插入失败");
			}
		} catch (Exception e) {
			log.error("国际临时外购付款申请单创建失败", e);
			throw new GSSException("创建国际临时外购付款申请单模块", "0107", "创建国际临时外购付款申请单模块失败");
		}
		return 1;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Page<OutSourceingApply> queryInRefApprefByVo(Page<OutSourceingApply> page,
			RequestWithActor<OutSourceingApplyVo> requestWithActor) {
		log.info("查询临时外购付款申请单开始=======");
		try {
			if (requestWithActor.getEntity() == null) {
				log.error("国际临时外购付款申请单对象为空");
				throw new GSSException("国际临时外购付款申请单查询对象为空", "0006", "查询临时外购付款申请单失败");
			}
			List<OutSourceingApply> list = outSourceingApplyDao.queryObjByKey(page, requestWithActor.getEntity());
			log.info(JsonUtil.toJson(list));
			page.setRecords(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("查询国际临时外购付款单结束=======");
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public OutSourceingApply getInRefundApprefById(String orderId) {
		OutSourceingApply outSourceingApply = null;
		try {
			outSourceingApply = outSourceingApplyDao.selectInRefundApprefVo(orderId);
		} catch (Exception e) {
			log.error("国际临时外购付款单查询失败", e);
			throw new GSSException("国际临时外购付款单查询模块", "0117", "国际临时外购付款单查询失败");
		}
		return outSourceingApply;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int updateInRefundAppref(Map<String, Object> map) {
		log.info("更新临时外购付款单开始");
		try {
			outSourceingApplyDao.updateStatus(map);
		} catch (Exception e) {
			log.error("国际临时外购付款单更新失败", e);
			throw new GSSException("国际临时外购付款单查询模块", "0108", "国际临时外购付款单模块更新失败");
		}
		log.info("更新临时外购付款申请单结束");
		return 1;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int updateByPrimaryKeySelective(OutSourceingApply outSourceingApply) {
		log.info("更新退款申请单开始");
		try {
			int flag = outSourceingApplyDao.updateByPrimaryKeySelective(outSourceingApply);
			if (flag == 0) {
				log.error("更新退款申请单");
				throw new GSSException("更新退款申请单模块", "0601", "退款申请单更新失败");
			}
		} catch (Exception e) {
			log.error("更新退款申请单", e);
			throw new GSSException("更新退款申请单模块", "0606", "退款申请单更新模块失败");
		}
		log.info("更新退款申请单结束");
		return 1;
	}

	@Override
	public OutSourceingApply getValueById(String orderId) {
		List<OutSourceingApply> outSourceingApplies = outSourceingApplyDao.selectValueById(orderId);
		if(null!=outSourceingApplies&&outSourceingApplies.size()>0){
			return  outSourceingApplies.get(0);
		}
		return null;
	}

	@Override
	public String getBankNameById(String bankId) {
		return outSourceingApplyDao.selectBankNameById(bankId);
	}

}