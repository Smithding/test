package com.tempus.gss.product.ift.service.policy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.tempus.gss.product.ift.api.entity.Leg;
import com.tempus.gss.product.ift.api.entity.Passenger;
import com.tempus.gss.product.ift.api.entity.policy.IftPolicy;
import com.tempus.gss.product.ift.api.entity.policy.IftPolicyQuery;
import com.tempus.gss.product.ift.api.entity.search.FlightQuery;
import com.tempus.gss.product.ift.api.service.policy.IftPolicyService;
import com.tempus.gss.product.ift.dao.policy.IftPolicyMapper;
import com.tempus.gss.product.ift.dao.policy.IftQueryPolicyMapper;
import com.tempus.gss.product.ift.help.IftLogHelper;
import com.tempus.gss.product.ift.help.IftPolicyHelper;
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
	
	@Autowired
	private IftQueryPolicyMapper iftQueryPolicyMapper;
	
	@Autowired
	private IftPolicyHelper policyHelper;
	
	/** 日志记录器. */
	protected static Logger logger = LogManager.getLogger(IftPolicyServiceImpl.class);
	
	@Override
	public long create(Agent agent, IftPolicy iftPolicy) {
		long policyId = IdWorker.getId();
		iftPolicy.setId(policyId);
		
		/* 设置默认值 */
		iftPolicy = this.setDefault(iftPolicy);
		EntityUtil.setAddInfo(iftPolicy, agent);
		iftPolicyMapper.insert(iftPolicy);
		IftLogHelper.logger(agent, policyId, "创建政策", packagePolicyLog(iftPolicy));
		return policyId;
	}

	@Override
	public IftPolicy find(Agent agent, long policyId) {
		return iftPolicyMapper.find(agent.getOwner(), policyId);
	}

	@Override
	public Page<IftPolicy> search(Agent agent, Page<IftPolicy> page, IftPolicyQuery query) {
		
		query.setOwner(agent.getOwner());
		// 调用底层先分页查询出符合国际政策ID列表
		List<Long> ids = iftPolicyMapper.query(page, query);
		// 根据国际政策ID列表循环拉取国际政策的详细
		List<IftPolicy> list = new ArrayList<>();
		for (Long id : ids) {
			// 通过ID拉取国际政策的信息
			IftPolicy detail = this.find(agent, id);
			list.add(detail);
		}
		
		// 设置分页分页查询的国际政策详情列表
		page.setRecords(list);
		return page;
	}
	
	@Override
	@Transactional
	public long update(Agent agent, IftPolicy iftPolicy) {
		
		/* 第一步设置原政策无效 */
		this.setInvalid(agent, iftPolicy.getId());
		IftLogHelper.logger(agent, iftPolicy.getId(), "删除政策", "编辑政策删除旧政策");
		
		/* 第二步新增一条政策 */
		long policyId = this.create(agent, iftPolicy);
		IftLogHelper.logger(agent, iftPolicy.getId(), "编辑政策", "该政策为编辑老政策["+policyId+"]生成的新政策");
		
		return policyId;
	}

	@Override
	public boolean setInvalid(Agent agent, long policyId) {
		Date modifyTime = new Date(System.currentTimeMillis());
		int count = iftPolicyMapper.setInvalid(agent.getOwner(), policyId, agent.getAccount(), modifyTime);
		return count > 0 ? true:false;
	}
	
	@Override
	public boolean changeStatus(Agent agent, Integer status, Long... policyIds) {
		Date modifyTime = new Date(System.currentTimeMillis());
		int count = iftPolicyMapper.changeStatus(agent.getOwner(), policyIds, status, agent.getAccount(), modifyTime);
		return count > 0 ? true:false;
	}
	
	@Override
	public boolean delete(Agent agent, Long... policyIds) {
		Date modifyTime = new Date(System.currentTimeMillis());
		int count = iftPolicyMapper.delete(agent.getOwner(), policyIds, agent.getAccount(), modifyTime);
		return count > 0 ? true:false;
	}
	
	@Override
	public List<IftPolicy> getPolicys(Agent agent, List<Leg> legs, double parPrice, int adtNumber, int chdNumber, int infNumber) {
		
		/* 第一步，组装条件从库里面获取政策 */
		FlightQuery query = policyHelper.packageQuery(agent, legs, adtNumber, chdNumber, infNumber);
		List<IftPolicy> iftPolicyList = iftQueryPolicyMapper.query(query);
		
		/* 第二步，再逐步过滤政策条件 */
		iftPolicyList = policyHelper.ruleFilter(iftPolicyList, legs, query, parPrice);
		
		return iftPolicyList;
	}
	
	@Override
	public List<IftPolicy> getPolicysByPnr(Agent agent, List<Passenger> passengers, List<Leg> legs,
			String pnr, String pnrContext) {
		
		/* 第一步，组装条件从库里面获取政策 */
		//TODO 儿童数以及婴儿数暂时还未获取
		FlightQuery query = policyHelper.packageQuery(agent, legs, passengers.size(), 0, 0);
		List<IftPolicy> iftPolicyList = iftQueryPolicyMapper.query(query);
		
		/* 第二步，再逐步过滤政策条件 */
		iftPolicyList = policyHelper.ruleFilterByPnr(iftPolicyList, passengers, legs, query, pnr, pnrContext);
		
		return iftPolicyList;
	}
	
	/**
	 * 组装国际政策代理费及奖励信息
	 * 
	 * @param policy 国际政策
	 * @return String 国际政策代理费及奖励内容 
	 */
	public String packagePolicyLog(IftPolicy policy){
		StringBuffer buffer = new StringBuffer();
		buffer.append("上游代理费：" + policy.getOriginalAgencyFee() + "\t上游奖励费用:" + policy.getOriginalRewardFee());
		buffer.append("\t下游代理费：" + policy.getAgencyFee() + "\t下游奖励费用:" + policy.getRewardFee());
		buffer.append("\t单程直减费用：" + policy.getOneWayPrivilege() + "\t往返直减费用:" + policy.getRoundTripPrivilege());
		buffer.append("\t开票费：" + policy.getOpenTicketFee());
		return buffer.toString();
	}
	
	/**
	 *  创建政策时设置默认值
	 * 
	 * @param iftPolicy  创建国际政策信息
	 * @return IftPolicy 设置默认值后的国际政策信息
	 */
	public IftPolicy setDefault(IftPolicy iftPolicy){
		
		if(null == iftPolicy.getSatIsTicket()){
			iftPolicy.setSatIsTicket(1); //默认星期六开票
		}
		if(null == iftPolicy.getSunIsTicket()){
			iftPolicy.setSunIsTicket(1); //默认星期日开票
		}
		if(null == iftPolicy.getIsNotsuitTransferAirport()){
			iftPolicy.setIsNotsuitTransferAirport(false); //默认不存在不适用转机机场
		}
		if(null == iftPolicy.getSuitDoublePeople()){
			iftPolicy.setSuitDoublePeople(false); //默认不适用双人
		}
		if(null == iftPolicy.getChangeAndRefund()){
			iftPolicy.setSuitDoublePeople(false); //默认不适用同改同退
		}
		if(null == iftPolicy.getChdRewardType()){
			iftPolicy.setChdRewardType(1); //儿童票奖励方式，1奖励与成人一致（默认）
		}
		if(null == iftPolicy.getPnrTicketType()){
			iftPolicy.setPnrTicketType(1);//PNR出票方式：1无需换编码（默认）
		}
		if(null == iftPolicy.getArnkType()){
			iftPolicy.setArnkType(1);//缺口程类型：1适用政策内缺口
		}
		if(null == iftPolicy.getShareRewardType()){
			iftPolicy.setShareRewardType(1); //共享奖励类型：1全程无奖励
		}
		return iftPolicy;
	}
	
	public static void main(String[] args) {
		String a = "ID, OWNER, NAME, SOURCE, VOYAGE_TYPE, TICKET_WAY, AIRLINE, SUIT_AIRLINE, SUPPLIERS, "+
	    "SUPPLIER_OFFICE, TICKET_OFFICE, ORIGINAL_AGENCY_FEE, ORIGINAL_REWARD_FEE, AGENCY_FEE, "+
	    "REWARD_FEE, ONE_WAY_PRIVILEGE, ROUND_TRIP_PRIVILEGE, OPEN_TICKET_FEE, AUTO_TICKET, "+
	    "IS_GDS, TICKET_RANGE, FLY_START_DATE, FLY_END_DATE, RTN_START_DATE, RTN_END_DATE, "+
	    "TICKET_VALID_START_DATE, TICKET_VALID_END_DATE, TICKET_DATE, SAT_IS_TICKET, SAT_TICKET_DATE,"+ 
	    "SUN_IS_TICKET, SUN_TICKET_DATE, FLIGHT_TYPE, DEPART_CABIN, TRANSIT_CABIN, ROUND_TRIP_AIRPORT_TYPE,"+ 
	    "ROUND_TRIP_AIRPORT, IS_NOTSUIT_TRANSFER_AIRPORT, NOTSUIT_TRANSFER_AIRPORT, SUIT_AGE, "+
	    "SUIT_PEOPLE_NUMBER, SUIT_DOUBLE_PEOPLE, CHANGE_AND_REFUND, CHD_REWARD_TYPE, CHD_ASSIGN_REWARD_FEE,"+ 
	    "CHD_IS_ADD_HANDLING_FEE, CHD_ADD_HANDLING_FEE, CHD_TICKET_NO_AGENCY_FEE, CHD_NOT_ALONE_TICKET, "+
	    "CHD_PRIVILEGE, INF_TICKET, INF_IS_ADD_HANDLING_FEE, INF_ADD_HANDLING_FEE, PNR_TICKET_TYPE, "+
	    "AUTO_CHANGE_PNR, MAKE_NULL_PNR, CHANGE_PNR_NOT_AUTH, ASSIGN_BOOK_OFFICE, BOOK_OFFICE, "+
	    "ASSIGN_BOOK_OFFICE_TYPE, NOTSUIT_PASSENGER_TYPE, ARNK_TYPE, ARNK_SUIT_COUNTRY, SHARE_REWARD_TYPE,"+ 
	    "SHARE_ASSIGN_REWARD, SHARE_LEG_REWARD, SHARE_IS_SUIT_AIRLINE, SHARE_SUIT_AIRLINE, "+
	    "IS_MIX_CABIN, MIX_CABIN_TYPE, IS_TOUR_CODE_INCLUDE_TEXT, TOUR_CODE_INCLUDE_TEXT, "+
	    "TOUR_CODE_NO_AGENCY, TOUR_CODE_NO_REWARD, TOUR_CODE_CONTENT, IS_ROUND_TRIP_INCLUDE_CABIN,"+ 
	    "ROUND_TRIP_INCLUDE_CABIN, IS_PAR_LIMIT, PAR_LIMIT, FARE_Q_AGENCY, FARE_Q_REWARD,"+
	    "DEPART_COUNTRY, DEPART_EXCLUDE_COUNTRY, DEPART_AIRPORT, DEPART_EXCLUDE_AIRPORT, TRANSIT_COUNTRY,"+ 
	    "TRANSIT_EXCLUDE_COUNTRY, TRANSIT_AIRPORT, TRANSIT_EXCLUDE_AIRPORT, ARRIVE_COUNTRY, "+
	    "ARRIVE_EXCLUDE_COUNTRY, ARRIVE_AIRPORT, ARRIVE_EXCLUDE_AIRPORT,  "+
	    "FARE_S_AGENCY, FARE_S_REWARD, IS_FARE_INCLUDE_TEXT, FARE_INCLUDE_TEXT, FARE_INCLUDE_TYPE,"+ 
	    "IS_SUIT_FARE_BASE, SUIT_FARE_BASE, IS_FLY_SUIT_FLIGHT, FLY_SUIT_FLIGHT, IS_RTN_SUIT_FLIGHT,"+ 
	    "RTN_SUIT_FLIGHT, IS_NOT_SUIT_FLIGHT, NOT_SUIT_FLIGHT, IS_NOT_SUIT_ROUTE, NOT_SUIT_ROUTE, "+
	    "FLY_NOTSUIT_CYCLE, RTN_NOTSUIT_CYCLE, FLY_NOTSUIT_DATE, RTN_NOTSUIT_DATE, REMARK, "+
	    "STATUS, CREATOR, CREATE_TIME, MODIFIER, MODIFY_TIME, VALID";
		String[] b = a.split(",");
		System.out.println(b.length);
	}

}
