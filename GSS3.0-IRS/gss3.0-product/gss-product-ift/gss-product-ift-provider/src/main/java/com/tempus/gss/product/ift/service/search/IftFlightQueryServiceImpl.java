package com.tempus.gss.product.ift.service.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.Flight;
import com.tempus.gss.product.ift.api.entity.QueryIBEDetail;
import com.tempus.gss.product.ift.api.entity.policy.IftPolicy;
import com.tempus.gss.product.ift.api.entity.search.FlightPolicy;
import com.tempus.gss.product.ift.api.entity.search.FlightQuery;
import com.tempus.gss.product.ift.api.entity.vo.FlightCabinPriceVo;
import com.tempus.gss.product.ift.api.entity.vo.FlightQueryRequest;
import com.tempus.gss.product.ift.api.service.search.IftFlightQueryService;
import com.tempus.gss.product.ift.dao.policy.IftQueryPolicyMapper;
import com.tempus.gss.util.Collections;
import com.tempus.gss.util.JsonUtil;
import com.tempus.gss.vo.Agent;

/**
 * 
 * <pre>
 * <b>根据航班信息匹配政策接口实现.</b>
 * <b>Description:</b> 
 *    
 * <b>Author:</b> cz
 * <b>Date:</b> 2018年9月4日 下午2:11:46
 * <b>Copyright:</b> Copyright ©2018 tempus.cn. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年9月4日 下午2:11:46    cz
 *         new file.
 * </pre>
 */
@Service
public class IftFlightQueryServiceImpl implements IftFlightQueryService {
	protected final transient Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private IftQueryPolicyMapper iftQueryPolicyMapper;
	@Autowired
	private IftFlightQueryUtils fftFlightQueryUtils;
	private IftPolicyRuleUtils ruleUtils;
	@Override
	public QueryIBEDetail mappingPriceSpec(QueryIBEDetail queryIBEDetail,RequestWithActor<FlightQueryRequest> request,String customerTypeNo, Agent agent) {
		// TODO Auto-generated method stub
		List<IftPolicy> iftPolicies = this.matcPolicy(request);
		/*if(!CollectionUtils.isEmpty(iftPolicies)){
				iftPolicies = Collections.filter(iftPolicies, (policy) -> 
				ruleUtils.matcheAirline(policy, queryIBEDetail.getTicketAirline()));
				queryIBEDetail.setIftPolicies(iftPolicies);
		}*/
		queryIBEDetail.setIftPolicies(iftPolicies);
		QueryIBEDetail detail = this.setSalePrice(queryIBEDetail);
		return detail;
	}
	private QueryIBEDetail setSalePrice(QueryIBEDetail queryIBEDetail){
		List<FlightPolicy> policyList = new ArrayList<FlightPolicy>();
		if(!CollectionUtils.isEmpty(queryIBEDetail.getIftPolicies())){
			FlightPolicy flightPolicy = new FlightPolicy();
			flightPolicy.setIftPolicy(queryIBEDetail.getIftPolicies().get(0));
			policyList.add(flightPolicy);
	   }
		queryIBEDetail = CalculatePriceUtils.fligthCalculate(queryIBEDetail,policyList, 2);
		return queryIBEDetail;
	}
	/**
	 * 获取到
	 * @param flights
	 * @return
	 *//*
	private Map<String,Object> getFlightCabin(List<Flight> flights){
		List<String> cabins ;//舱位信息
		String [] rtnCabins;//回程舱位
		String [] flightNo;
		String [] rtnFlightNo;
		for (Flight flight : flights) {
			List<FlightCabinPriceVo> flightCabinPriceVos = flight.getFlightCabinPriceVos();
			for (FlightCabinPriceVo flightCabinPriceVo : flightCabinPriceVos) {
				if(flight.getDuration().equals("go")){//去程
					if(flightCabinPriceVo.getPassengerType().equals("ADT")){//获取成人的舱位信息
					}
				}else{//回程
					
				}
			}
			
		}
	}*/
	@Override
	public List<IftPolicy> matcPolicy(RequestWithActor<FlightQueryRequest> request) {
		FlightQuery query = fftFlightQueryUtils.getFlightQueryParam(request);
		System.out.println(JsonUtil.toJson(request));
		List<IftPolicy> iftPolicyList = iftQueryPolicyMapper.query(query);
		return iftPolicyList;
	}
}
