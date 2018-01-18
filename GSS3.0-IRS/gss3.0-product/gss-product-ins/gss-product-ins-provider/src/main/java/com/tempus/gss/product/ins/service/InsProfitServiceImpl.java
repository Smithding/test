package com.tempus.gss.product.ins.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.bbp.util.StringUtil;
import com.tempus.gss.cps.entity.Channel;
import com.tempus.gss.cps.service.IChannelService;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.hol.api.entity.Profit;
import com.tempus.gss.product.hol.api.entity.ProfitPrice;
import com.tempus.gss.product.hol.api.entity.vo.ProfitVo;
import com.tempus.gss.product.ins.api.entity.InsProfit;
import com.tempus.gss.product.ins.api.entity.InsProfitPrice;
import com.tempus.gss.product.ins.api.entity.ProfitStatement;
import com.tempus.gss.product.ins.api.entity.vo.InsProfitVo;
import com.tempus.gss.product.ins.api.service.IInsProfitService;
import com.tempus.gss.product.ins.dao.InsProfitDao;
import com.tempus.gss.product.ins.dao.ProfitControlDao;
import com.tempus.gss.system.service.IMaxNoService;
import com.tempus.gss.util.JsonUtil;
import com.tempus.gss.vo.Agent;


@Service
@EnableAutoConfiguration
public class InsProfitServiceImpl implements IInsProfitService{
	
	@Autowired
	ProfitControlDao profitControlDao;
	@Autowired
	InsProfitDao insProfitDao;
	
	@Reference
	protected IChannelService channelService;
	
	@Reference
    IMaxNoService maxNoService;
	
	protected final transient Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	public Page<InsProfit> queryProfitList(Page<InsProfit> page, Agent agent, InsProfitVo vo) {
		
		
	//	List<ProfitControl> insProfitList = profitControlDao.queryProfitList(page, vo);
		
		List<InsProfit> profitList = insProfitDao.queryProfitList(page, vo);
		
		page.setRecords(profitList);
		
		return page;
	}

	@Override
	public int createProfit(Agent agent, InsProfitVo vo) {
		if(agent ==null ){
			log.error("agent为空");
            throw new GSSException("创建保险控润失败", "0103", "agent为空");
		}
		if(vo==null){
			log.error("agent为空");
            throw new GSSException("创建保险控润失败", "0103", "传入参数为空");
		}
		try{
			List<Channel> channelList=channelService.getTree(agent, vo.getCustomerTypeNo(), 4);
			if(StringUtil.isNullOrEmpty(channelList)){
				throw new GSSException("创建保险控润失败", "0309", "根据客商类型查询渠道失败");
			}else{
				for(Channel channel:channelList){
					//创建所有渠道
					this.createVo(agent,vo,channel);
				}
			}
			/*boolean flag=this.pushJson(agent,vo);
			if(!flag){
				return 0;
			}*/
		}catch(Exception e){
			log.error("创建保险控润失败");
			throw new GSSException("创建保险控润失败", "0302", "创建保险控润失败"+e.getMessage());
		}
		return 1;
	}
	
	public int createVo(Agent agent, InsProfitVo vo,Channel channel){
		int flag=0;
		try{
			Long profitNo=maxNoService.generateBizNo("INS_PROFIT_NO", 63);
			InsProfit profit=new InsProfit();
			profit.setCustomerTypeName(channel.getName());
			profit.setCustomerTypeNo(channel.getNo());
			profit.setOwner(agent.getOwner());
            profit.setModifier(agent.getAccount());
			profit.setParentId(channel.getType());//父级ID
			System.out.println("规则: "+ JsonUtil.toJson(vo.getReturnCashRuleObj()));
			//控润模式 1.控点 2.控现
			if(vo.getRebateRuleObj()==null){
				profit.setProfitMode(2);
				profit.setPriceType(vo.getReturnCashRuleObj().getMode());
				for(InsProfitPrice ins : vo.getReturnCashRuleObj().getRules()){
					profit.setProfitMoney(ins.getRate());
				}
			}
			if(vo.getReturnCashRuleObj()==null){
				profit.setProfitMode(1);
				profit.setPriceType(vo.getRebateRuleObj().getMode());
				for(InsProfitPrice ins : vo.getRebateRuleObj().getRules()){
					profit.setProfitCount(ins.getRate());
				}
			}
			profit.setInsuranceNo(vo.getInsuranceNo());
			profit.setInsuranceName(vo.getInsuranceName());
			profit.setProfitNo(profitNo);
			profit.setRemark(vo.getRemark());
			profit.setStatus(vo.getStatus());
			profit.setValid(vo.getValid());
			profit.setCreateTime(new Date());
			profit.setCreator(agent.getAccount());
			
			
			flag=insProfitDao.insertSelective(profit);
			
		}catch(Exception e){
			log.error("创建保险控润失败");
			throw new GSSException("创建保险控润失败", "0302", "创建保险控润失败"+e.getMessage());
		}
		return flag;
	}

	@Override
	public InsProfit selectByProfitNo(Long profitNo,Agent agent) {
		if(agent==null || profitNo == null){
			throw new GSSException("根据customerTypeNo获取政策错误", "0302", "agent/传入参数为空");
		}
		InsProfit insProfit = new InsProfit();
		try{
			insProfit = insProfitDao.selectByProfitNo(profitNo);
			if(insProfit!=null){// && profit.getPayType()!=1
				ProfitStatement statement=new ProfitStatement();
				ProfitStatement profitStatement=new ProfitStatement();
				if(insProfit.getProfitMode()==1){
					statement.setMode(insProfit.getPriceType());
					insProfit.setRebateRule(statement);
					profitStatement.setMode(2);
					profitStatement.setRules(null);
					insProfit.setReturnCashRule(profitStatement);
				}
				if(insProfit.getProfitMode()==2){
					statement.setMode(insProfit.getPriceType());
					insProfit.setReturnCashRule(statement);
					profitStatement.setMode(1);
					profitStatement.setRules(null);
					insProfit.setRebateRule(profitStatement);
				}
			}
		}catch(Exception e){
			log.error("根据customerTypeNo获取政策错误"+e);
			throw new GSSException("根据customerTypeNo获取政策错误", "0302", "查询出错！");
		}
		return insProfit;
	}

	@Override
	public InsProfit updateByProfitNo(InsProfitVo vo ,Agent agent) {
		if(agent==null || vo == null){
			throw new GSSException("修改政策", "0302", "agent/传入参数为空");
		}
		InsProfit profit = new InsProfit();
		profit.setId(vo.getId());
		
		try{
			if(vo.getRebateRuleObj()==null){
				profit.setProfitMode(2);
				profit.setPriceType(vo.getReturnCashRuleObj().getMode());
				for(InsProfitPrice ins : vo.getReturnCashRuleObj().getRules()){
					profit.setProfitMoney(ins.getRate());
					profit.setProfitCount(new BigDecimal(0));
				}
			}
			if(vo.getReturnCashRuleObj()==null){
				profit.setProfitMode(1);
				profit.setPriceType(vo.getRebateRuleObj().getMode());
				for(InsProfitPrice ins : vo.getRebateRuleObj().getRules()){
					profit.setProfitCount(ins.getRate());
					profit.setProfitMoney(new BigDecimal(0));
				}
			}
			/*profit.setCustomerTypeName(vo.getCustomerTypeName());
			profit.setCustomerTypeNo(vo.getCustomerTypeNo());
			List<Channel> channelList=channelService.getTree(agent, vo.getCustomerTypeNo(), 4);
			for(Channel cc : channelList){
				profit.setParentId(cc.getType());
			}*/
			profit.setModifier(agent.getAccount());
			profit.setModifierTime(new Date());
			profit.setRemark(vo.getRemark());
			profit.setStatus(vo.getStatus());
			profit.setValid(vo.getValid());
			//int profitFlag = insProfitDao.updateSelective(profit, profit);
			int profitFlag = insProfitDao.updateSelectiveById(profit);
			//int profitFlag = insProfitDao.updateByProfitNo(vo);
			
			if(profitFlag == 1){
				profit= insProfitDao.selectByProfitNo(vo.getProfitNo());
			}else{
				throw new GSSException("修改政策", "0302", "修改失败！");
			}
		}catch(Exception e){
			log.error("修改政策"+e);
			throw new GSSException("修改政策", "0302", "查询出错！");
		}
		return profit;
		
		
		
	}

	@Override
	public InsProfit selectByProfitNoAndCustomerTypeNo(InsProfitVo vo, Agent agent) {
		if(agent==null || vo.getProfitNo() == null || vo.getCustomerTypeNo()==null){
			throw new GSSException("根据customerTypeNo获取政策错误", "0302", "agent/传入参数为空");
		}
		InsProfit profit=new InsProfit();
		InsProfitPrice insProfitPrice=new InsProfitPrice();
		ProfitStatement statement=new ProfitStatement();
		try{
			profit=insProfitDao.queryProfitList(vo);
					if(profit.getProfitMode()==1){
						List<InsProfitPrice> profitPriceList =new ArrayList<InsProfitPrice>();
						insProfitPrice.setRate(profit.getProfitCount());
						profitPriceList.add(insProfitPrice);
						profit.setProfitPriceList(profitPriceList);
						statement.setMode(profit.getPriceType());
						statement.setRules(profitPriceList);
						profit.setRebateRule(statement);
					}
					if(profit.getProfitMode()==2){
						List<InsProfitPrice> profitPriceList =new ArrayList<InsProfitPrice>();
						insProfitPrice.setRate(profit.getProfitMoney());
						profitPriceList.add(insProfitPrice);
						statement.setMode(profit.getPriceType());
						statement.setRules(profitPriceList);
						profit.setReturnCashRule(statement);
					}
			
		}catch(Exception e){
			log.error(""+e);
		}
		return profit;
		
	}

	@Override
	public int canceByInsProfitNo(Agent agent, Long id) {
		int flag=0;
		try {
			if(agent == null){
				log.error("agent为空");
			    throw new GSSException("取消保险控润失败", "0103", "agent为空");
			}
			if(id ==null){
				log.error("id为空");
			    throw new GSSException("取消保险控润失败", "0103", "id为空");
			}
			InsProfitVo vo =new InsProfitVo();
			vo.setModifier(agent.getAccount());
			vo.setModifierTime(new Date());
			vo.setValid(0);
			vo.setProfitNo(id);
			flag = insProfitDao.updateByProfitNo(vo);
		} catch (Exception e) {
			log.error("取消酒店供应商失败", e);
			throw new GSSException("取消保险控润失败", "0302", "取消保险控润失败");
		}
		return flag;
	
	}

	@Override
	public Boolean queryProfitResList(Agent agent, InsProfitVo vo) {
		InsProfitVo insVo = new InsProfitVo();
		insVo.setCustomerTypeNo(vo.getCustomerTypeNo());
		insVo.setInsuranceNo(vo.getInsuranceNo());
		List<InsProfit> resList = insProfitDao.queryProfitResList(insVo);
		for(InsProfit insprofit : resList){
			if(vo.getCustomerTypeNo().equals(insprofit.getCustomerTypeNo()) && vo.getInsuranceNo().equals(insprofit.getInsuranceNo())){
				return false;
			}
		}
		return true;
	}

	@Override
	public List<InsProfit> queryProfitListAll(InsProfitVo vo) {
		// TODO Auto-generated method stub
		List<InsProfit> profitList  = null;
	     profitList = insProfitDao.queryProfitListAll(vo);
		return profitList;
	}

}
