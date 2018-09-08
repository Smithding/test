package com.tempus.gss.product.ift.service.search;

import java.util.List;

import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.policy.IftPolicy;
import com.tempus.gss.product.ift.api.entity.vo.FlightQueryRequest;
import com.tempus.gss.product.ift.api.service.search.IftFlightQueryService;

/**
 * 白屏航班匹配政策
 * @author juan.yin
 *
 */
public class FlightPolicyRun implements Runnable {
    private IftFlightQueryService iftFlightQueryService;
    private RequestWithActor<FlightQueryRequest> request;
    private List<IftPolicy> IftPolicyList;
    public List<IftPolicy> getIftPolicyList() {
		return IftPolicyList;
	}
	public FlightPolicyRun(IftFlightQueryService iftFlightQueryService,RequestWithActor<FlightQueryRequest> request) {
		// TODO Auto-generated constructor stub
    	this.iftFlightQueryService = iftFlightQueryService;
    	this.request =request;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		IftPolicyList = iftFlightQueryService.matcPolicy(request);
	}
	
}
