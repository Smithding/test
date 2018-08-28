package com.tempus.gss.product.ift.service.policy;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.tempus.gss.product.ift.api.entity.policy.IftPolicy;
import com.tempus.gss.product.ift.api.entity.policy.IftPolicyQuery;
import com.tempus.gss.product.ift.api.service.policy.IftPolicyService;
import com.tempus.gss.product.ift.dao.policy.IftPolicyMapper;
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
		
	@Override
	public long create(Agent agent, IftPolicy iftPolicy) {
		long policyId = IdWorker.getId();
		iftPolicy.setId(policyId);
		EntityUtil.setAddInfo(iftPolicy, agent);
		iftPolicyMapper.insert(iftPolicy);
		return policyId;
	}

	@Override
	public IftPolicy find(Agent agent, long policyId) {
		return iftPolicyMapper.find(agent.getOwner(), policyId);
	}

	@Override
	public Page<IftPolicy> search(Agent agent, IftPolicyQuery query) {
		
		return null;
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
