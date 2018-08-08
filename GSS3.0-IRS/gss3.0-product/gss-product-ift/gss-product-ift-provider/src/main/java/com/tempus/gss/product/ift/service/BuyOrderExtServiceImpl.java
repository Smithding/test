package com.tempus.gss.product.ift.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.order.entity.BuyOrder;
import com.tempus.gss.order.service.IBuyOrderService;
import com.tempus.gss.product.ift.api.entity.BuyOrderExt;
import com.tempus.gss.product.ift.api.service.IBuyOrderExtService;
import com.tempus.gss.product.ift.dao.BuyOrderExtDao;
import com.tempus.gss.vo.Agent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/21 0021.
 */
@Service
@Component
@EnableAutoConfiguration
public class BuyOrderExtServiceImpl implements IBuyOrderExtService {

	protected final transient Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	BuyOrderExtDao buyOrderExtDao;
	@Reference
	IBuyOrderService buyOrderService;

	@Override
	public BuyOrderExt selectByBuyOrderNo(Agent agent,Long buyOrderNo) throws Exception{
		log.info("获取采购单拓展开始==");
		BuyOrderExt buyOrderExt = null;
		try {
			if (agent == null || buyOrderNo == null) {
				log.error("所需参数为空");
				throw new GSSException("所需参数为空", "1001", "获取采购单拓展失败");
			}
			 buyOrderExt = buyOrderExtDao.selectByPrimaryKey(buyOrderNo);
			if(buyOrderExt==null){
				log.error("获取采购单拓展为空,buyOrderNo="+buyOrderNo);
				return null;
			}
			BuyOrder buyOrder = buyOrderService.getBOrderByBONo(agent, buyOrderNo);
			if (buyOrder != null)
				buyOrderExt.setBuyOrder(buyOrder);
		}catch (Exception e){
			log.error("获取采购单拓展", e);
		}
		log.info("获取采购单拓展结束==");
		return buyOrderExt;
	}


	@Override
	public BuyOrderExt selectBySaleOrderNo(Agent agent,Long saleOrderNo) throws Exception{
		log.info("获取采购单拓展开始==");
		BuyOrder buyOrder = null;
		BuyOrderExt buyOrderExt = null;
		try {
			if (agent == null || saleOrderNo == null) {
				log.error("所需参数为空");
				throw new GSSException("所需参数为空", "1001", "获取采购单拓展失败");
			}
			List<BuyOrder> buyOrderList = buyOrderService.getBuyOrdersBySONo(agent, saleOrderNo);

			if (buyOrderList != null && buyOrderList.size() != 0) {
				buyOrder = buyOrderList.get(0);//国际订单为1销售单对应1采购单
			}

			if (buyOrder != null) {
				buyOrderExt = buyOrderExtDao.selectByPrimaryKey(buyOrder.getBuyOrderNo());
			}
			if(buyOrderExt!=null){
				buyOrderExt.setBuyOrder(buyOrder);
			}

		}catch (Exception e){
			log.error("获取采购单失败"+e);
			throw new GSSException("获取采购单失败"+e, "1001", "获取采购单失败");
		}
		log.info("获取采购单拓展结束==");
		return buyOrderExt;
	}

	@Override
	public List<BuyOrderExt> selectListBySaleOrderNo(Agent agent,Long saleOrderNo) throws Exception{
		log.info("获取采购单拓展开始==");
		List<BuyOrderExt> buyOrderExtList = new ArrayList<>();
		try {
			if (agent == null || saleOrderNo == null) {
				log.error("所需参数为空");
				throw new GSSException("所需参数为空", "1001", "获取采购单拓展失败");
			}
			List<BuyOrder> buyOrderList = buyOrderService.getBuyOrdersBySONo(agent, saleOrderNo);

			if (buyOrderList != null&&buyOrderList.size()!=0) {
				for(BuyOrder buyOrder:buyOrderList) {
					BuyOrderExt buyOrderExt = buyOrderExtDao.selectByPrimaryKey(buyOrder.getBuyOrderNo());
					if(buyOrderExt!=null){
						buyOrderExt.setBuyOrder(buyOrder);
						buyOrderExtList.add(buyOrderExt);
					}
				}
			}

		}catch (Exception e){
			log.error("获取采购单失败"+e);
			throw new GSSException("获取采购单失败"+e, "1001", "获取采购单失败");
		}
		log.info("获取采购单拓展结束==");
		return buyOrderExtList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public int update(Agent agent, BuyOrderExt buyOrderExt) {
		return buyOrderExtDao.updateByPrimaryKeySelective(buyOrderExt);
	}

/*	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void updateBuyOrderExt(BuyOrderExt buyOrderExt) {
		buyOrderExtDao.updateByPrimaryKeySelective(buyOrderExt);
	}*/
}
