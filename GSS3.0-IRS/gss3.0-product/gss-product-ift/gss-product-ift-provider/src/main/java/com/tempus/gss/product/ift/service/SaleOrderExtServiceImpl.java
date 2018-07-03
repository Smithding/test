package com.tempus.gss.product.ift.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.order.entity.SaleChange;
import com.tempus.gss.order.entity.SaleOrder;
import com.tempus.gss.order.service.ISaleChangeService;
import com.tempus.gss.order.service.ISaleOrderService;
import com.tempus.gss.product.ift.api.entity.SaleOrderExt;
import com.tempus.gss.product.ift.api.service.ISaleOrderDetailService;
import com.tempus.gss.security.AgentUtil;
import com.tempus.gss.vo.Agent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import com.alibaba.dubbo.config.annotation.Service;
import com.tempus.gss.product.ift.api.service.ISaleOrderExtService;
import com.tempus.gss.product.ift.dao.SaleOrderExtDao;

import java.util.ArrayList;
import java.util.List;

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
	@Reference
	ISaleChangeService saleChangeService;
	@Reference
	ISaleOrderDetailService detailService;

	@Override
	public SaleOrderExt selectBySaleOrderNo(Agent agent,Long saleOrderNo) throws Exception{
		log.info("获取销售单拓展开始==");
		if(agent==null||saleOrderNo==null){
			log.error("所需参数为空");
			throw new GSSException("所需参数为空","1001","获取销售单拓展失败");
		}
		SaleOrderExt saleOrderExt = saleOrderExtDao.selectByPrimaryKey(saleOrderNo);

		//获取所有的改签的detail加入到saleOrderExt的detail中去
		List<SaleChange> saleChangeList = saleChangeService.getSaleChangesBySONo(agent,saleOrderNo);
		List<Long> saleChangeNoList = new ArrayList<>();
		for (com.tempus.gss.order.entity.SaleChange saleChange : saleChangeList) {
			if(saleChange.getOrderChangeType() == 3){
				saleChangeNoList.add(saleChange.getSaleChangeNo());
			}
		}
		if(saleChangeNoList != null&& saleChangeNoList.size()>0 ){
			List<com.tempus.gss.product.ift.api.entity.SaleOrderDetail> saleOrderDetails = detailService.selectBySaleChangeNoList(saleChangeNoList);
			if(saleOrderDetails!=null && saleOrderDetails.size()>0){
				saleOrderExt.getSaleOrderDetailList().addAll(saleOrderDetails);
			}
		}

		SaleOrder saleOrder = saleOrderService.getSOrderByNo(agent,saleOrderNo);
		if(saleOrderExt !=null && saleOrder!=null)
			saleOrderExt.setSaleOrder(saleOrder);
		log.info("获取销售单拓展结束==");
		return saleOrderExt;
	}

	@Override
	public int updateByPrimaryKeySelective(SaleOrderExt saleOrderExt) {
		if(saleOrderExt == null){
			return 0;
		}
		return saleOrderExtDao.updateByPrimaryKeySelective(saleOrderExt);
	}
}
