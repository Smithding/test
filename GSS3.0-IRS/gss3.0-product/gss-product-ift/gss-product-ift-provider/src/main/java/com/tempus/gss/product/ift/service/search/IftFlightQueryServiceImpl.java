package com.tempus.gss.product.ift.service.search;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.QueryIBEDetail;
import com.tempus.gss.product.ift.api.entity.policy.IftPolicy;
import com.tempus.gss.product.ift.api.entity.search.FlightQuery;
import com.tempus.gss.product.ift.api.entity.vo.FlightQueryRequest;
import com.tempus.gss.product.ift.api.service.search.IftFlightQueryService;
import com.tempus.gss.product.ift.dao.policy.IftQueryPolicyMapper;
import com.tempus.gss.vo.Agent;

public class IftFlightQueryServiceImpl implements IftFlightQueryService {
	protected final transient Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private IftQueryPolicyMapper iftQueryPolicyMapper;
	@Autowired
	private IftFlightQueryUtils fftFlightQueryUtils;
	@Override
	public void mappingPriceSpec(QueryIBEDetail queryIBEDetail,List<IftPolicy> policyList,String customerTypeNo, Agent agent) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public List<IftPolicy> matcPolicy(RequestWithActor<FlightQueryRequest> request) {
		FlightQuery query = fftFlightQueryUtils.getFlightQueryParam(request);
		List<IftPolicy> iftPolicyList = iftQueryPolicyMapper.query(query);
		return iftPolicyList;
	}
}
