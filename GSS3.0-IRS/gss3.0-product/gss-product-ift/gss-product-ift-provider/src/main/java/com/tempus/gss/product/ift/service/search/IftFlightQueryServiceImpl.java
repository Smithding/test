package com.tempus.gss.product.ift.service.search;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.QueryIBEDetail;
import com.tempus.gss.product.ift.api.entity.policy.IftFlightPolicy;
import com.tempus.gss.product.ift.api.entity.policy.IftPolicy;
import com.tempus.gss.product.ift.api.entity.policy.IftPolicyChange;
import com.tempus.gss.product.ift.api.entity.search.FlightQuery;
import com.tempus.gss.product.ift.api.entity.vo.FlightQueryRequest;
import com.tempus.gss.product.ift.api.service.search.IftFlightQueryService;
import com.tempus.gss.product.ift.dao.policy.IftQueryPolicyMapper;
import com.tempus.gss.util.JsonUtil;
import com.tempus.gss.vo.Agent;
import com.tempus.tbe.entity.AvailableJourney;

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
	protected static final Logger log = LogManager.getLogger(IftFlightQueryServiceImpl.class);
	@Autowired
	private IftQueryPolicyMapper iftQueryPolicyMapper;
	@Autowired
	private IftFlightQueryUtils fftFlightQueryUtils;
	private IftPolicyRuleUtils ruleUtils;
	@Override
	public QueryIBEDetail mappingPriceSpec(QueryIBEDetail queryIBEDetail,List<IftPolicy> iftPolicyList,String customerTypeNo, Agent agent) {
		// TODO Auto-generated method stub
		//List<IftPolicy> iftPolicies = this.matcPolicy(request);
		/*if(!CollectionUtils.isEmpty(iftPolicyList)){
			iftPolicyList = Collections.filter(iftPolicyList, (policy) -> 
				ruleUtils.matcheAirline(policy, queryIBEDetail.getTicketAirline()));
				queryIBEDetail.setIftPolicies(iftPolicyList);
		}*/
		QueryIBEDetail detail  = CalculatePriceUtils.fligthCalculate(queryIBEDetail,iftPolicyList, 1);
		return detail;
	}
	@Override
	public List<IftPolicy> matcPolicy(RequestWithActor<FlightQueryRequest> request) {
		if(request.getEntity().getJson()!=null&&!request.getEntity().getJson().equals("")){//当json不为空的时候，是点更多舱位查询航班数据
			AvailableJourney availableJourney = JSONObject.parseObject(request.getEntity().getJson(), AvailableJourney.class);
			FlightQueryRequest flightQueryRequest = request.getEntity();
			flightQueryRequest.setAirline(availableJourney.getOdOption().get(0).getFlight().get(0).getMarketingAirline());//出票航司
			request.setEntity(flightQueryRequest);
		}
		FlightQuery query = fftFlightQueryUtils.getFlightQueryParam(request);
		List<IftPolicy> iftPolicyList = iftQueryPolicyMapper.query(query);
		return iftPolicyList;
	}
	@Override
	public List<IftPolicyChange> orderPolicy(QueryIBEDetail queryIBEDetail,List<IftPolicy> policys) {
		// TODO Auto-generated method stub
		List<IftPolicyChange> policyChanges = CalculatePriceUtils.orderPolicyCalculate(queryIBEDetail,policys,1);
		return policyChanges;
	}
}
