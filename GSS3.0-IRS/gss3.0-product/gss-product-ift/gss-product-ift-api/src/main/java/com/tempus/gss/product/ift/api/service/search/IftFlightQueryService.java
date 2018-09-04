package com.tempus.gss.product.ift.api.service.search;

import java.util.List;

import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.QueryIBEDetail;
import com.tempus.gss.product.ift.api.entity.policy.IftPolicy;
import com.tempus.gss.product.ift.api.entity.vo.FlightQueryRequest;
import com.tempus.gss.vo.Agent;

public interface IftFlightQueryService {
	 /**航班白屏列表查询；根据shoppring结果匹配政策*/
	 public void mappingPriceSpec(QueryIBEDetail queryIBEDetail,List<IftPolicy> policyList, String customerTypeNo, Agent agent); 
	 
	 /**根据查询白屏航班条件获取政策信息*/
	 public List<IftPolicy> matcPolicy(RequestWithActor<FlightQueryRequest> request);
}
