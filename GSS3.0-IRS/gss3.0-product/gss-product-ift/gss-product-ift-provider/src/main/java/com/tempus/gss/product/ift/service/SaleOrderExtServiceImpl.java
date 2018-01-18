package com.tempus.gss.product.ift.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.order.entity.SaleOrder;
import com.tempus.gss.order.service.ISaleOrderService;
import com.tempus.gss.product.ift.api.entity.SaleOrderExt;
import com.tempus.gss.vo.Agent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import com.alibaba.dubbo.config.annotation.Service;
import com.tempus.gss.product.ift.api.service.ISaleOrderExtService;
import com.tempus.gss.product.ift.dao.SaleOrderExtDao;

/**
 * Created by Administrator on 2016/9/21 0021.
 */
@Service
@org.springframework.stereotype.Service
@EnableAutoConfiguration
public class SaleOrderExtServiceImpl implements ISaleOrderExtService {

	protected final transient Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	SaleOrderExtDao saleOrderExtDao;
	@Reference
	ISaleOrderService saleOrderService;

	@Override
	public SaleOrderExt selectBySaleOrderNo(Agent agent,Long saleOrderNo) throws Exception{
		log.info("获取销售单拓展开始==");
		if(agent==null||saleOrderNo==null){
			log.error("所需参数为空");
			throw new GSSException("所需参数为空","1001","获取销售单拓展失败");
		}
		SaleOrderExt saleOrderExt = saleOrderExtDao.selectByPrimaryKey(saleOrderNo);
		SaleOrder saleOrder = saleOrderService.getSOrderByNo(agent,saleOrderNo);
		if(saleOrderExt !=null && saleOrder!=null)
			saleOrderExt.setSaleOrder(saleOrder);
		log.info("获取销售单拓展结束==");
		return saleOrderExt;
	}
}
