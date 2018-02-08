package com.tempus.gss.product.hol.service;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.framework.service.impl.SuperServiceImpl;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.bbp.util.StringUtil;
import com.tempus.gss.cps.entity.Channel;
import com.tempus.gss.cps.service.IChannelService;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.hol.api.entity.HolSupplier;
import com.tempus.gss.product.hol.api.entity.Profit;
import com.tempus.gss.product.hol.api.entity.ProfitPrice;
import com.tempus.gss.product.hol.api.entity.response.QTPrice;
import com.tempus.gss.product.hol.api.entity.vo.ProfitStatement;
import com.tempus.gss.product.hol.api.entity.vo.ProfitVo;
import com.tempus.gss.product.hol.api.entity.vo.QueryProfitPrice;
import com.tempus.gss.product.hol.api.service.IHolProfitService;
import com.tempus.gss.product.hol.dao.HolSupplierMapper;
import com.tempus.gss.product.hol.dao.ProfitMapper;
import com.tempus.gss.product.hol.dao.ProfitPriceMapper;
import com.tempus.gss.system.service.IMaxNoService;
import com.tempus.gss.util.JsonUtil;
import com.tempus.gss.vo.Agent;

/**
 *
 * Profit 表数据服务层接口实现类
 *
 */
@Service
@EnableAutoConfiguration
public class HolProfitServiceImpl extends SuperServiceImpl<ProfitMapper, Profit> implements IHolProfitService {

	protected final transient Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	ProfitMapper profitMapper;
	@Autowired
	ProfitPriceMapper profitPriceMapper;
	@Autowired
	HolSupplierMapper supplierMapper;
	@Reference
    IMaxNoService maxNoService;
	@Reference
	protected IChannelService channelService;
	@Override
	public Page<Profit> queryProfitList(Page<Profit> page, Agent agent, ProfitVo vo) {
		log.info("酒店政策查询开始");
		try {
			if(agent==null){
				log.error("agent为空");
	            throw new GSSException("酒店控润查询失败", "0101", "agent为空");
			}
			vo.setOwner(agent.getOwner());
			List<Profit> profitList = profitMapper.queryProfitList(page, vo);
			if(profitList!=null && profitList.size()>0){
				for(Profit profit:profitList){
					if(profit.getPriceType()!=1){
						List<ProfitPrice> profitPriceList=profitPriceMapper.selectByProfitNo(profit.getProfitNo());
						profit.setProfitPriceList(profitPriceList);
					}
				}
			}
			page.setRecords(profitList);
		} catch (Exception e) {
			log.error("酒店政策查询失败", e);
			throw new GSSException("酒店政策查询模块", "0302", "查询失败");
		}
		log.info("酒店政策查询结束");
		return page;
	}
	
	@Override
	public int createProfit(Agent agent, ProfitVo vo) {
		if(agent ==null ){
			log.error("agent为空");
            throw new GSSException("创建酒店控润失败", "0103", "agent为空");
		}
		if(vo==null){
			log.error("agent为空");
            throw new GSSException("创建酒店控润失败", "0103", "传入参数为空");
		}else{
			if(StringUtil.isNullOrEmpty(vo.getCustomerTypeNo())){
				throw new GSSException("创建酒店控润失败", "0302", "传入customerTypeNo为空");
			}
		}
		try{
			ProfitVo latestvo = new ProfitVo();
			latestvo.setCustomerTypeNo(vo.getCustomerTypeNo());
		
			List<Profit> profitList = profitMapper.selectProfitList(latestvo);
			if(StringUtil.isNotNullOrEmpty(profitList)){
				for(Profit po : profitList){
					if(po.getCustomerTypeNo().equals(vo.getCustomerTypeNo()) && po.getSupplierSource().equals(vo.getSupplierSource())){
						return -1;
					}
				}
			}
			List<Channel> channelList=channelService.getTree(agent, vo.getCustomerTypeNo(), 4);
			for(Channel channel:channelList){
				//创建所有渠道
				this.createVo(agent,vo,channel);
			}
		}catch(Exception e){
			logger.error("创建酒店控润失败"+e);
			throw new GSSException("创建酒店控润失败", "0302", "创建酒店控润失败");
		}
		return 1;
	}
	
	public int createVo(Agent agent, ProfitVo vo,Channel channel){
		int flag=0;
		try{
			HolSupplier supplier=supplierMapper.queryBySupplierCode(vo.getSupplierSource());
			Long profitNo=maxNoService.generateBizNo("HOL_PROFIT_NO", 33);
			Profit profit=new Profit();
			profit.setCustomerTypeName(channel.getName());
			profit.setCustomerTypeNo(channel.getNo());
			profit.setOwner(agent.getOwner());
			profit.setParentId(channel.getType());//父级ID
			if(supplier!=null){
				profit.setPayType(supplier.getPayType());//根据供应商管理中的供应商支付方式传
			}
			//控润模式 1.控点 2.控现
			if(vo.getRebateRuleObj()==null){
				profit.setProfitMode(2);
				profit.setPriceType(vo.getReturnCashRuleObj().getMode());
			}
			if(vo.getReturnCashRuleObj()==null){
				profit.setProfitMode(1);
				profit.setPriceType(vo.getRebateRuleObj().getMode());
			}
			profit.setProfitNo(profitNo);
			profit.setRemark(vo.getRemark());
			profit.setStatus(vo.getStatus());
			profit.setSupplierSource(vo.getSupplierSource());
			profit.setValid(vo.getValid());
			profit.setCreateTime(new Date());
			profit.setCreator(agent.getAccount());
			profit.setPayType(supplier.getPayType());
			flag=profitMapper.insertSelective(profit);
			//插入控润价格
			vo.setProfitNo(profitNo);
			flag=this.createProfitPrice(vo,agent);
			if(flag == 0){
				throw new GSSException("插入控润错误", "0302", "插入控润错误！");
			}
		}catch(Exception e){
			logger.error("创建酒店控润失败"+e);
			throw new GSSException("创建酒店控润失败", "0302", "创建酒店控润失败");
		}
		return flag;
	}

	@Override
	public Profit selectByProfitNo(Long profitNo,Agent agent) {
		if(agent==null || profitNo == null){
			throw new GSSException("根据customerTypeNo获取政策错误", "0302", "agent/传入参数为空");
		}
		Profit profit=new Profit();
		try{
			profit=profitMapper.selectByProfitNo(profitNo);
			if(profit!=null){// && profit.getPayType()!=1
				List<ProfitPrice> profitPriceList=profitPriceMapper.selectByProfitNo(profit.getProfitNo());
				if(profitPriceList.size()==0 || profitPriceList.size()<0){
					profitPriceList=null;
				}
				ProfitStatement statement=new ProfitStatement();
				ProfitStatement profitStatement=new ProfitStatement();
				if(profit.getProfitMode()==1){
					statement.setMode(profit.getPriceType());
					statement.setRules(profitPriceList);
					profit.setRebateRule(statement);
					profitStatement.setMode(2);
					profitStatement.setRules(null);
					profit.setReturnCashRule(profitStatement);
				}
				if(profit.getProfitMode()==2){
					statement.setMode(profit.getPriceType());
					statement.setRules(profitPriceList);
					profit.setReturnCashRule(statement);
					profitStatement.setMode(1);
					profitStatement.setRules(null);
					profit.setRebateRule(profitStatement);
				}
			}
		}catch(Exception e){
			logger.error("根据customerTypeNo获取政策错误"+e);
			throw new GSSException("根据customerTypeNo获取政策错误", "0302", "查询出错！");
		}
		
		return profit;
	}

	/**
	 * 修改政策
	 */
	@Override
	public Profit updateByProfitNo(Agent agent, ProfitVo vo) {
		if(agent==null || vo == null){
			throw new GSSException("修改政策", "0302", "agent/传入参数为空");
		}
		Profit profit=new Profit();
		try{
			HolSupplier supplier=supplierMapper.queryBySupplierCode(vo.getSupplierSource());
			//控润模式 1.控点 2.控现
			if(supplier!=null){
				vo.setPayType(supplier.getPayType());//根据供应商管理中的供应商支付方式传
			}
			if(vo.getRebateRuleObj()==null){
				vo.setProfitMode(2);
				vo.setPriceType(vo.getReturnCashRuleObj().getMode());
			}
			if(vo.getReturnCashRuleObj()==null){
				vo.setProfitMode(1);
				vo.setPriceType(vo.getRebateRuleObj().getMode());
			}
			vo.setModifier(agent.getAccount());
			vo.setModifierTime(new Date());
			int profitFlag =profitMapper.updateByProfitNo(vo);
			
			//更新控润价格
			ProfitStatement statement = new ProfitStatement();
			
			Profit profit_o = this.selectByProfitNo(vo.getProfitNo(), agent);
			ProfitStatement statement_o = new ProfitStatement();
			
			if(vo.getProfitMode() == 1){
				statement = vo.getRebateRuleObj();
				statement_o = profit_o.getRebateRule();
			}else if(vo.getProfitMode() == 2){
				statement = vo.getReturnCashRuleObj();
				statement_o = profit_o.getReturnCashRule();
			}
			List<ProfitPrice> profitPriceList = statement.getRules();
			String priceNos = "";
			for (ProfitPrice profitPrice : profitPriceList) {
				if(null != profitPrice.getPriceNo()){
					priceNos += profitPrice.getPriceNo()+",";
					profitPrice.setProfitNo(vo.getProfitNo());
					profitPrice.setModifier(vo.getModifier());
					profitPrice.setModifyTime(vo.getModifierTime());
					profitFlag = profitPriceMapper.updateByProfitNoAndPriceNo(profitPrice);
				}else{
					profitFlag = this.createtPrice(vo, agent,profitPrice,statement.getMode());
				}
			}
			
			List<ProfitPrice> profitPriceList_o = statement_o.getRules();
			for (ProfitPrice profitPrice_o : profitPriceList_o) {
				//不包含的说明执行了删除操作
				if(!priceNos.contains(profitPrice_o.getPriceNo().toString())){
					profitFlag = profitPriceMapper.delectByPriceNo(profitPrice_o.getPriceNo());
				}
			}
			
			if(profitFlag == 1){
				profit=profitMapper.selectByProfitNo(vo.getProfitNo());
			}else{
				throw new GSSException("修改政策", "0302", "修改失败！");
			}
			/*boolean flag=this.pushJson(agent,vo);
			if(!flag){
				return null;
			}*/
		}catch(Exception e){
			logger.error("修改政策"+e);
			throw new GSSException("修改政策", "0302", "查询出错！");
		}
		return profit;
	}
	
	public int createProfitPrice(ProfitVo vo,Agent agent){
		int flag=0;
		if(vo.getReturnCashRuleObj()!=null && vo.getReturnCashRuleObj().getMode()!=1){
			for(ProfitPrice price:vo.getReturnCashRuleObj().getRules()){
				flag=this.createtPrice(vo, agent,price,vo.getReturnCashRuleObj().getMode());
			}
		}
		if(vo.getRebateRuleObj()!=null && vo.getRebateRuleObj().getMode()!=1){
			for(ProfitPrice price:vo.getRebateRuleObj().getRules()){
				flag=this.createtPrice(vo, agent,price,vo.getRebateRuleObj().getMode());
			}
		}
		if((vo.getReturnCashRuleObj()!=null && vo.getReturnCashRuleObj().getMode()==1)
				||(vo.getRebateRuleObj()!=null && vo.getRebateRuleObj().getMode()==1)){
			flag=1;
		}
		if(flag == 0){
			throw new GSSException("插入控润价格错误", "0302", "插入控润价格错误！");
		}
		return flag;
	}
	
	public int createtPrice(ProfitVo vo,Agent agent,ProfitPrice price,int priceType){
		price.setPriceNo(maxNoService.generateBizNo("HOL_PROFIT_PRICE_NO", 56));
		price.setPriceType(priceType);
		price.setRate(price.getRate());
		price.setProfitNo(vo.getProfitNo());
		price.setCreator(agent.getAccount());
		price.setCreateTime(new Date());
		price.setOwner(agent.getOwner().longValue());
		price.setRemark(vo.getRemark());
		price.setStatus(vo.getStatus());
		price.setValid(vo.getValid());
		int flag=profitPriceMapper.insertSelective(price);
		if(flag == 0){
			throw new GSSException("插入控润价格错误", "0302", "插入控润价格错误！");
		}
		return flag;
	}
	
	//进行数据推送
	/*public boolean pushJson(Agent agent,ProfitVo vo){
		List<PricePolicy> pricePolicyList =new ArrayList<PricePolicy>();
		
		List<Profit> profitList=profitMapper.selectProfitList(new ProfitVo());
		
		for(Profit profit:profitList){
			//过滤除UBP内销的//所有UBP内销下的，以307开头，如：307,307001,307002
			if(profit.getValid() == 1 && profit.getStatus().equals("1") && profit.getCustomerTypeNo().toString().startsWith("307")){
				PricePolicy pricePolicy=new PricePolicy();
				Dictionary<String,String> supplier=new Hashtable<String,String>();
				supplier.put(profit.getSupplierSource(), profit.getSupplierSource());
				pricePolicy.setId(profit.getProfitNo().toString());
				pricePolicy.setPriceType(profit.getPriceType());
				pricePolicy.setPayType(profit.getPayType());
				pricePolicy.setSupplierSource(agent.getOwner().toString());//"8023"
				
				Dictionary<String,String> clients=new Hashtable<String,String>();
				clients.put(vo.getCustomerTypeNo().toString(), "");
				pricePolicy.setClients(clients);
					
				pricePolicy.setSuppliers(supplier);
				List<ProfitPrice> profitPriceList=profitPriceMapper.selectByProfitNo(profit.getProfitNo());
				List<PriceFromTo> priceFromTo=new ArrayList<PriceFromTo>();
				if(profitPriceList!=null && profitPriceList.size()>0){
					for(ProfitPrice price:profitPriceList){
						PriceFromTo fromTo=new PriceFromTo();
						fromTo.setPriceFrom(price.getPriceFrom());
						fromTo.setPriceTo(price.getPriceTo());
						fromTo.setRate(price.getRate());
						priceFromTo.add(fromTo);
					}
				}
				pricePolicy.setPrices(priceFromTo);
				pricePolicyList.add(pricePolicy);
			}
		}
		
		//进行数据推送
		PricePolicyReq pricePolicyReq=new PricePolicyReq();
		pricePolicyReq.setPolicys(pricePolicyList);
		pricePolicyReq.setUBPCode(agent.getOwner().toString());
		QTCommonRequest qtCommonRequest = new QTCommonRequest();
        qtCommonRequest.setAction(ActionType.PricePolicy.toString());
        qtCommonRequest.setRequestJson(JSONObject.toJSONString(pricePolicyReq));
        String reqJson = JSONObject.toJSONString(qtCommonRequest);
        BaseResponse baseResponse = HttpClientUtil.doJsonPost(QianTaoConfig.SUPPLIER_URL, reqJson, BaseResponse.class);
        if (baseResponse != null) {
            if (baseResponse.getIsSuccess()) {
                logger.info("推送成功！"+reqJson);
            } else {
                logger.error("推送失败");
                throw new GSSException("推送供应商信息失败", "0110", "推送供应商信息失败:" + baseResponse.getErrorMessage());
            }
        } else {
            logger.error("推送供应商信息返回空值");
            throw new GSSException("推送供应商信息请求", "0111", "推送供应商信息返回空值");
        }
		return true;
	}*/

	@Override
	public Profit selectByProfitNoAndCustomerTypeNo(ProfitVo vo, Agent agent) {
		if(agent==null || vo.getProfitNo() == null || vo.getCustomerTypeNo()==null){
			throw new GSSException("根据customerTypeNo获取政策错误", "0302", "agent/传入参数为空");
		}
		Profit profit=new Profit();
		ProfitStatement statement=new ProfitStatement();
		try{
			profit=profitMapper.queryProfitList(vo);
			if(profit!=null && profit.getProfitNo()!=null){
				if(profit.getPriceType()!=1){
					List<ProfitPrice> profitPriceList=profitPriceMapper.selectByProfitNo(profit.getProfitNo());
					profit.setProfitPriceList(profitPriceList);
					if(profit.getProfitMode()==1){
						statement.setMode(profit.getPriceType());
						statement.setRules(profitPriceList);
						profit.setRebateRule(statement);
					}
					if(profit.getProfitMode()==2){
						statement.setMode(profit.getPriceType());
						statement.setRules(profitPriceList);
						profit.setReturnCashRule(statement);
					}
				}
			}
		}catch(Exception e){
			log.error(""+e);
		}
		return profit;
	}
	
	@Override
	public BigDecimal computeProfitPrice(Long customerTypeNo,QTPrice qtPrice, Agent agent) {
		List<Profit> profitList=new ArrayList<>();
		BigDecimal min=null;
		try{
			if(agent == null || customerTypeNo==null){
				throw new GSSException("根据customerTypeNo获取政策错误", "0302", "agent/传入参数为空");
			}
			ProfitVo vo=new ProfitVo();
			vo.setCustomerTypeNo(customerTypeNo);
			vo.setPriceType(1);
			profitList=profitMapper.selectProfitList(vo);
			if(profitList!=null && profitList.size()>0){
				return qtPrice.getPrice();
			}else{
				vo.setPriceType(null);
				profitList=profitMapper.selectProfitList(vo);
				if(profitList!=null && profitList.size()>0){
					for(Profit profit:profitList){
						//根据价格区间查询返回
						QueryProfitPrice query=new QueryProfitPrice();
						//1 预付 2 现付
						BigDecimal price = new BigDecimal(0);
						//根据支付类型取对应控润的价格
						if(profit.getPayType() == 1){
							price = qtPrice.getReduce();//立减
						}else{
							price = qtPrice.getRefund();//返现
						}
						min = price;
						query.setPrice(price);
						List<ProfitPrice> profitPriceList=profitPriceMapper.queryProfitPriceList(query);
						profit.setProfitPriceList(profitPriceList);

						//控润后价格集合，有多条重复控润时，取最低控润后价格
						List<BigDecimal> pricea =new ArrayList<BigDecimal>();
						if(profitPriceList!=null && profitPriceList.size()>0){
							for(int i=0;i<profitPriceList.size();i++){
								for(ProfitPrice profitPrice:profitPriceList){
									if(profitPrice.getPriceType()==3){
										//控点（控润计算公式：价格-（价格*点数*0.01））四舍五入保留1位小数
										pricea.add(i,price.subtract(price.multiply(profitPrice.getRate().multiply(new BigDecimal(0.01)))).setScale(0, BigDecimal.ROUND_HALF_UP));
									}
									if(profitPrice.getPriceType()==2){
										//控钱（控润计算公式：价格-控现数值）
										pricea.add(i, price.subtract(profitPrice.getRate()));
									}
								}
							}
						}
						//循环最低价格返回
						if(pricea !=null && pricea.size()>0){
							min = pricea.get(0);
							for (BigDecimal bigDecimal:pricea){
								if(bigDecimal.compareTo(min)<0){
									min=bigDecimal;
								}
							}
						}
						//重新设置控润后的价格，要验证不能为负，当为负时不控润
						if(profit.getPayType() == 1){
							if(min.compareTo(qtPrice.getReduce())!=1)
								qtPrice.setReduce(min);//立减
						}else{
							if(min.compareTo(qtPrice.getRefund())!=1)
								qtPrice.setRefund(min);//返现
						}
					}
				}else{
					return qtPrice.getPrice();
				}
			}
			//循环最低价格返回
			/*if(pricea !=null && pricea.size()>0){
				min = pricea.get(0);
				for (BigDecimal bigDecimal:pricea){
					if(bigDecimal.compareTo(min)<0){
						min=bigDecimal;
					}
				}
			}*/
		}catch(Exception e){
			throw new GSSException("根据customerTypeNo查询失败","1001","根据customerTypeNo查询失败"+e);
		}
		return min;
	}

	@Override
	public BigDecimal computeTcProfitPrice(Agent agent, Integer price, Long customerTypeNo) {
		//List<Profit> profitList=new ArrayList<>();
		//BigDecimal res= null;
		log.info("酒店控润查询开始, 入参: 价格: "+price+", customerTypeNo: "+customerTypeNo);
		BigDecimal min=null;
		try {
			if(agent == null || customerTypeNo==null){
				throw new GSSException("根据customerTypeNo获取政策错误", "0302", "agent/传入参数为空");
			}
			/*ProfitVo vo=new ProfitVo();
			vo.setCustomerTypeNo(customerTypeNo);
			profitList=profitMapper.selectProfitList(vo);
			System.out.println("profitList: "+JsonUtil.toJson(profitList));*/
			
			List<BigDecimal> pricea =new ArrayList<BigDecimal>();
			QueryProfitPrice query=new QueryProfitPrice();
			query.setPrice(new BigDecimal(price));
			query.setCustomerTypeNo(customerTypeNo);
			List<ProfitPrice> profitPriceList=profitPriceMapper.queryProfitPriceList(query);
			if(StringUtil.isNullOrEmpty(profitPriceList)){
				List<Channel> channelList=channelService.getTree(agent, customerTypeNo, 4);
				if(StringUtil.isNotNullOrEmpty(channelList)){
						for(Channel chanss : channelList){
							if(chanss.getLevel().equals(4L)){
								if(chanss.getChilds().size() > 0){
									query.setCustomerTypeNo(chanss.getChilds().get(0).getThreeType());
									profitPriceList=profitPriceMapper.queryProfitPriceList(query);
									if(StringUtil.isNullOrEmpty(profitPriceList)){
										query.setCustomerTypeNo(chanss.getChilds().get(0).getTwoType());
										profitPriceList=profitPriceMapper.queryProfitPriceList(query);
										if(StringUtil.isNullOrEmpty(profitPriceList)){
											query.setCustomerTypeNo(chanss.getChilds().get(0).getOneType());
											profitPriceList=profitPriceMapper.queryProfitPriceList(query);
											if(StringUtil.isNullOrEmpty(profitPriceList)){
												throw new GSSException("根据customerTypeNo查询失败","1001","根据customerTypeNo查询失败,该customerTypeNo无对应策略组 ");
											}
										}
									}
								}
							}
							if(chanss.getLevel().equals(3L)){
								if(chanss.getChilds().size() > 0){
									query.setCustomerTypeNo(chanss.getChilds().get(0).getTwoType());
									profitPriceList=profitPriceMapper.queryProfitPriceList(query);
									if(StringUtil.isNullOrEmpty(profitPriceList)){
										query.setCustomerTypeNo(chanss.getChilds().get(0).getOneType());
										profitPriceList=profitPriceMapper.queryProfitPriceList(query);
										if(StringUtil.isNullOrEmpty(profitPriceList)){
											throw new GSSException("根据customerTypeNo查询失败","1001","根据customerTypeNo查询失败,该customerTypeNo无对应策略组 ");
										}
									}
								}
							}
							if(chanss.getLevel().equals(2L)){
								if(chanss.getChilds().size() > 0){
									query.setCustomerTypeNo(chanss.getChilds().get(0).getOneType());
									profitPriceList=profitPriceMapper.queryProfitPriceList(query);
									if(StringUtil.isNullOrEmpty(profitPriceList)){
										throw new GSSException("根据customerTypeNo查询失败","1001","根据customerTypeNo查询失败,该customerTypeNo无对应策略组 ");
									}
								}
							}
							if(chanss.getLevel().equals(1L)){
								throw new GSSException("根据customerTypeNo查询失败","1001","根据customerTypeNo查询失败,该customerTypeNo为最高组级无对应控润");
							}
						/*	if(StringUtil.isNullOrEmpty(profitPriceList)){
								List<Channel> channelList2=channelService.getTree(agent, chanss.getType(), 4);
								if(StringUtil.isNotNullOrEmpty(channelList2)){
									for(Channel chans2 : channelList2){
										query.setCustomerTypeNo(chans2.getType());
										profitPriceList=profitPriceMapper.queryProfitPriceList(query);
										if(StringUtil.isNullOrEmpty(profitPriceList)){
											List<Channel> channelList3=channelService.getTree(agent, chans2.getType(), 4);
											if(StringUtil.isNotNullOrEmpty(channelList3)){
												for(Channel chans3 : channelList3){
													query.setCustomerTypeNo(chans3.getType());
													profitPriceList=profitPriceMapper.queryProfitPriceList(query);
													if(StringUtil.isNullOrEmpty(profitPriceList)){
														throw new GSSException("根据customerTypeNo查询失败","1001","根据customerTypeNo查询失败,该customerTypeNo无对应策略组 ");
													}
												}
											}
										}
									}
								}
							}*/
						}
				}
			}
			if(profitPriceList != null && profitPriceList.size()<2){
				min = profitPriceList.get(0).getRate();
			}else{
				for(ProfitPrice fit : profitPriceList){
					pricea.add(fit.getRate());
				}
				min = Collections.min(pricea);
			}
			if(min != null){
				min = min.multiply(new BigDecimal(0.01)).setScale(2, BigDecimal.ROUND_HALF_UP);
			}
			
			/*if(profitList!=null && profitList.size()>0){
				for(Profit profit:profitList){
					if(profit.getPayType() == 2){
						if(profitPriceList!=null && profitPriceList.size()>0){
							for(int i=0;i<profitPriceList.size();i++){
								for(ProfitPrice profitPrice:profitPriceList){
									res = profitPrice.getRate().multiply(new BigDecimal(0.01)).setScale(2, BigDecimal.ROUND_HALF_UP);
									System.out.println("res: "+res);
									pricea.add(i, res);
								}
							}
						}
						//循环最低点数返回
						if(pricea !=null && pricea.size()>0){
							min = pricea.get(0);
							for (BigDecimal bigDecimal:pricea){
								if(bigDecimal.compareTo(min)<0){
									min=bigDecimal;
								}
							}
						}
					}
				}
			}*/
		} catch (Exception e) {
			throw new GSSException("根据customerTypeNo查询失败","1001","根据customerTypeNo查询失败,customerTypeNo无对应价格控润 "+e.getMessage());
		}
		return min;
	}

	@Override
	public int cancelByProfitNo(Agent agent, Long id) {
		int flag=0;
		try {
			if(agent == null){
				log.error("agent为空");
			    throw new GSSException("取消酒店控润失败", "0103", "agent为空");
			}
			if(id ==null){
				log.error("id为空");
			    throw new GSSException("取消酒店控润失败", "0103", "id为空");
			}
			ProfitVo vo =new ProfitVo();
			vo.setModifier(agent.getAccount());
			vo.setModifierTime(new Date());
			vo.setValid(0);
			vo.setProfitNo(id);
			flag = profitMapper.updateByProfitNo(vo);
		} catch (Exception e) {
			log.error("取消酒店供应商失败", e);
			throw new GSSException("取消酒店控润失败", "0302", "取消酒店控润失败");
		}
		return flag;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}