/**
 * ProfitServiceImpl.java
 * com.tempus.gss.product.ift.service
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2017年9月7日 		shuo.cheng
 *
 * Copyright (c) 2017, TNT All Rights Reserved.
*/

package com.tempus.gss.product.ift.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tempus.gss.cps.entity.Channel;
import com.tempus.gss.cps.entity.Customer;
import com.tempus.gss.cps.service.IChannelService;
import com.tempus.gss.cps.service.ICustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.ift.api.entity.Profit;
import com.tempus.gss.product.ift.api.entity.vo.ProfitVO;
import com.tempus.gss.product.ift.api.service.IProfitService;
import com.tempus.gss.product.ift.dao.ProfitDao;
import com.tempus.gss.system.service.IMaxNoService;
import com.tempus.gss.vo.Agent;


/**
 * ClassName:ProfitServiceImpl
 * Function: 国际控润实现类
 *
 * @author   shuo.cheng
 * @version  
 * @since    Ver 1.1
 * @Date	 2017年9月7日		上午10:08:43
 *
 * @see 	 
 *  
 */
@Service
@EnableAutoConfiguration
public class ProfitServiceImpl implements IProfitService {

	protected final transient Logger log = LoggerFactory.getLogger(getClass());

	@Reference
	IMaxNoService maxNoService;

	@Autowired
	ProfitDao profitDao;
	@Reference
	protected IChannelService channelService;
	@Reference
	protected ICustomerService customerService;

	@Override
	public Page<Profit> queryProfitList(Page<Profit> page, Agent agent, ProfitVO vo) {

		log.info("国际控润政策查询开始");
		try {
			if (agent == null) {
				log.error("agent为空");
				throw new GSSException("国际控润政策查询失败", "0101", "agent为空");
			}
			List<Profit> profitList = profitDao.queryProfitList(page, vo);
			page.setRecords(profitList);
		} catch (Exception e) {
			log.error("国际控润政策查询失败", e);
			throw new GSSException("国际控润政策查询模块", "0302", "查询失败");
		}
		log.info("国际控润政策查询结束");
		return page;
	}

	@Override
	public int createProfit(Agent agent, ProfitVO vo) {

		if (agent == null) {
			log.error("agent为空");
			throw new GSSException("创建国际控润失败", "0103", "agent为空");
		}
		if (vo == null) {
			log.error("传入参数为空");
			throw new GSSException("创建国际控润失败", "0103", "传入参数为空");
		}

		int flag = 0;
		try {
			List<Channel> channelList;
			if(vo.getCustomerTypeNo()<1000000&&vo.getCustomerTypeNo()>1000) {
				channelList = channelService.getTree(agent, vo.getCustomerTypeNo(), 4);
				vo.setCustomerNo(null);
			}else {
				Customer customer = customerService.getCustomerByNo(agent, vo.getCustomerTypeNo());
				channelList = channelService.getTree(agent, customer.getGroupNo(), 4);
				vo.setCustomerName(customer.getName());
				vo.setCustomerNo(customer.getCustomerNo());
			}
			for (Channel channel : channelList) {
				//创建所有渠道
				this.createVo(agent, vo, channel);
			}
		} catch (Exception e) {
			log.error("创建国际控润失败" + e);
			throw new GSSException("创建国际控润失败", "0302", "创建国际控润失败");
		}
		return flag;
	}

	public int createVo(Agent agent, ProfitVO vo, Channel channel) {
		int i;
		try {
			Long profitNo = maxNoService.generateBizNo("IFT_PROFIT_NO", 59);
			Profit profit = new Profit();
			profit.setCustomerTypeName(channel.getName());
			profit.setCustomerTypeNo(channel.getNo());
			profit.setOwner(agent.getOwner());
			profit.setCreateTime(new Date());
			profit.setCreator(agent.getAccount());
			if (null != vo.getRebate()) {
				profit.setPriceType(2);
			} else {
				profit.setPriceType(3);
			}
			profit.setProfitNo(profitNo);
			profit.setRebate(vo.getRebate());
			profit.setRaisePrice(vo.getRaisePrice());
			profit.setCustomerNo(vo.getCustomerNo());
			profit.setCustomerName(vo.getCustomerName());
			profit.setStatus(vo.getStatus());
			profit.setModifyTime(new Date());
			profit.setRemark(vo.getRemark());
			i = profitDao.insertSelective(profit);
			if (i == 0) {
				throw new GSSException("插入控润错误", "0302", "插入控润错误！");
			}
		} catch (Exception e) {
			throw new GSSException("创建国际机票控润失败", "0302", "创建国际机票控润失败");
		}
		return i;
	}

	@Override
	public int updateByProfitNo(Agent agent, Profit profit) {
		int result = 0;
		if (agent == null) {
			log.error("agent为空");
			throw new GSSException("更新国际控润失败", "0103", "agent为空");
		}
		if (profit == null) {
			log.error("传入参数为空");
			throw new GSSException("更新国际控润失败", "0103", "传入参数为空");
		}

		if (null == profit.getCustomerNo()) {
			if(profit.getPriceType()==1){
				profit.setRebate(null);
				profit.setRaisePrice(null);
			}else if(profit.getPriceType()==2){
				profit.setRaisePrice(null);
			}else if(profit.getPriceType()==3){
				profit.setRebate(null);
			}
			result = profitDao.updateByPrimaryKeySelective(profit);
		} else {

			try {
				List<Channel> channelList;
				if(profit.getCustomerNo()<1000000&&profit.getCustomerNo()>1000) {
					channelList = channelService.getTree(agent, profit.getCustomerNo(), 4);
					profit.setCustomerNo(null);
				}else {
					Customer customer = customerService.getCustomerByNo(agent, profit.getCustomerNo());
					channelList = channelService.getTree(agent, customer.getGroupNo(), 4);
					profit.setCustomerName(customer.getName());
				}
				for (Channel channel : channelList) {
					//创建所有渠道
					this.updateVo(agent, profit, channel);
				}
			} catch (Exception e) {
				log.error("更新国际控润失败" + e);
				throw new GSSException("更新国际控润失败", "0103", "修改国际控润失败");
			}
		}

		return result;
	}

	public int updateVo(Agent agent,  Profit vo, Channel channel) {
		int i;
		try {
			vo.setCustomerTypeName(channel.getName());
			vo.setCustomerTypeNo(channel.getNo());
			vo.setModifyTime(new Date());
			if(vo.getPriceType()==1){
				vo.setRebate(null);
				vo.setRaisePrice(null);
			}else if(vo.getPriceType()==2){
				vo.setRaisePrice(null);
			}else if(vo.getPriceType()==3){
				vo.setRebate(null);
			}
			i = profitDao.updateByPrimaryKeySelective(vo);
			if (i == 0) {
				throw new GSSException("更新控润错误", "0103", "更新控润错误！");
			}
		} catch (Exception e) {
			throw new GSSException("更新国际机票控润失败", "0103", "更新国际机票控润失败");
		}
		return i;
	}

	@Override
	public Profit selectByProfitNo(Long profitNo, Agent agent) {

		if (agent == null) {
			log.error("agent为空");
			throw new GSSException("查询国际控润失败", "0103", "agent为空");
		}
		if (profitNo == null) {
			log.error("传入参数为空");
			throw new GSSException("查询国际控润失败", "0103", "传入参数为空");
		}

		return profitDao.selectByPrimaryKey(profitNo);

	}

	@Override
	public int deleteByProfitNo(Long profitNo, Agent agent) {
		if (agent == null) {
			log.error("agent为空");
			throw new GSSException("删除国际控润失败", "0103", "agent为空");
		}
		if (profitNo == null) {
			log.error("传入参数为空");
			throw new GSSException("删除国际控润失败", "0103", "传入参数为空");
		}
		int i = profitDao.deleteByPrimaryKeySelective(profitNo);
		if (i == 0) {
			log.error("传入参数有误");
			throw new GSSException("删除国际控润失败", "0103", "传入参数有误");
		}
		return i;
	}

}

