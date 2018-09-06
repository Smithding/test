package com.tempus.gss.product.ift.api.service.search;

import java.util.List;

import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.QueryIBEDetail;
import com.tempus.gss.product.ift.api.entity.policy.IftPolicy;
import com.tempus.gss.product.ift.api.entity.vo.FlightQueryRequest;
import com.tempus.gss.vo.Agent;

/**
 * 
 * <pre>
 * <b>根据航班信息匹配政策接口.</b>
 * <b>Description:</b> 
 *    
 * <b>Author:</b> cz
 * <b>Date:</b> 2018年9月4日 下午2:11:52
 * <b>Copyright:</b> Copyright ©2018 tempus.cn. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年9月4日 下午2:11:52    cz
 *         new file.
 * </pre>
 */

public interface IftFlightQueryService {
	 /**航班白屏列表查询；根据shoppring结果匹配政策*/
	 public QueryIBEDetail mappingPriceSpec(QueryIBEDetail queryIBEDetail,RequestWithActor<FlightQueryRequest> request, String customerTypeNo, Agent agent); 
	 
	 /**根据查询白屏航班条件获取政策信息*/
	 public List<IftPolicy> matcPolicy(RequestWithActor<FlightQueryRequest> request);
}
